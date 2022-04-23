<template>
	<view > 
		<view class="title">
			<image src="../../static/my/touxiang1.png" mode=""></image>
		</view>
		<view class="up_view">
			<text class="up"></text>
			<view class="title_login">账号登录</view>
			<view class="">
				<view class="login dept" @click="selectDept"  >
					<input type="text" disabled="true" v-model="deptName" placeholder="请选择部门" placeholder-style="color:#1296DB;" />
					<image  src="../../static/login/xia.svg" style="margin-left:40px;display:inline-block;height: 15px;" mode=""></image>
					<u-select v-model="show" mode="mutil-column-auto" :list="dept" @confirm="confirm" label-name='name' value-name="id" child-name="childList"></u-select>
				</view>
				<view class="login">
					<image src='../../static/login/user.png' mode=""></image>
					<input type="text" v-model="loginForm.username" :placeholder="user_placeholder" placeholder-style="color:gray;"/>
				</view>
				<view class="login ">
					<image src='../../static/login/psd.png' mode=""></image>
					<input type="text" v-model="loginForm.password" password placeholder="密码" placeholder-style="color:gray;" />
				</view>
				<view class="login" v-if="codeImg">
					<input type="number" v-model="loginForm.code" placeholder="请输入验证码" placeholder-style="color:#1296DB;"/>
					<image class="code_img" :src="codeImg" @click="getCode" ></image>
				</view>
				<view class="button" @click="toSkip()">登  录</view>
				<text style="color:#F7AA3D;text-align: right;display: block;font-size: 14px;margin:20px 15px" @click="forget()">忘记密码</text>
			
			</view>
		</view>
	</view>
</template>

<script>
	import {login,getCode,getAllDept} from '../../api/login.js'
	import baseUrl from '@/utils/env.js';
	import  Axios from '@/utils/request.js'
	const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD\n' +
	  '2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ=='
	const Encrypt = require('@/utils/jsencrypt.js')
	let cryptFirst = new Encrypt.JSEncrypt()
	cryptFirst.setPrivateKey(publicKey)

	export default{
		data(){
			return{
				user_placeholder:'账号',
				discribe:'所属机构账号登录',
				loginForm:{
					username:'',
					password:'',
					code:'',
					uuid:'',
				},
				codeImg:'',
				dept:[],
				deptName:'',
				deptId:null,
				show:false
			}
		},
		
		onLoad(){
			this.getDept();
			// this.getCodeImg();
		},
		methods:{
			selectDept(){
				this.show=true
			},
			//点击确定，获取该部门的id
			confirm(e){
				var num = e.length
				this.deptName = e[num-1].label
				this.deptId = e[num-1].value
			},
			//登录
			// login(){
			// 	let user = {
			// 	    username: this.loginForm.username,
			// 	    password: cryptFirst.encrypt(this.loginForm.password),
			// 	    code: this.loginForm.code,
			// 		deptId:this.deptId,
			// 	    uuid: this.loginForm.uuid
			// 	}
			// 	login(user).then(res=>{
			// 		console.log('res.data---------------',res)
			// 		uni.setStorageSync("token",res.data.token)
			// 		if(res.code == 0){
			// 			Axios.isLogin = true
			// 			//返回上一个页面
			// 			uni.navigateBack({
			// 			    delta: 1
			// 			});
			// 			console.log("是否返回")
			// 		}else{
			// 			this.loginForm=[]
			// 			console.log("错误信息")
			// 			this.getCodeImg()
			// 			console.log("再次请求")
			// 		}
			// 	}).catch(erro=>{
			// 		// this.getCodeImg()
			// 		this.loginForm.code = ''
			// 	})
				
			// },
			login(){
				var that=this;
				let user = {
				    username: this.loginForm.username,
				    password: cryptFirst.encrypt(this.loginForm.password),
				    code: this.loginForm.code,
					deptId:this.deptId,
				    uuid: this.loginForm.uuid
				}
				uni.request({
					url:baseUrl+'/auth/reception/login',
					method:'post',
					data:user,
					success: (res) => {
						console.log(res)
						uni.setStorageSync("token",res.data.data.token)
						if(res.data.code == 0){
							console.log(12222222)
							Axios.isLogin = true
							//返回上一个页面
							uni.navigateBack({
								delta: 1
							});
						}else{
							console.log(2333333)
							this.loginForm.code = ''
							if(res.data.data.isVerify){
								console.log(566666)
								that.codeImg= res.data.data.img
								that.loginForm.uuid = res.data.data.uuid
								console.log(that.codeImg,'that.codeImg')
							}
							return uni.showToast({
								title: res.data.message
							})
						}
					},
					fail:(err)=>{
						that.loginForm=[]
						return uni.showToast({
							title: '数据请求出错！'
						})
					}
					
				})
			
			},
			//获取所有部门
			getDept(){
				getAllDept().then(res=>{
					this.dept = res.data
					if(res){
						this.dept.forEach(item=>{
							if(item.children == null){
								item.children = {}
							}
						})
					}else{
						return uni.showToast({
							title: '数据请求出错！'
						})
					}
					
				})
			},
			// //获取验证码
			// getCodeImg(){
			// 	var that=this;
			// 	uni.request({
			// 		url:baseUrl+'/auth/code',
			// 		method:'get',
			// 		success: (res) => {
			// 			that.codeImg= res.data.data.img
			// 			that.loginForm.uuid = res.data.data.uuid
			// 		},
			// 		fail:(err)=>{
			// 			that.loginForm=[]
			// 			return uni.showToast({
			// 				title: '数据请求出错！'
			// 			})
			// 		}
					
			// 	})
			
			// },
			//登录
			toSkip(){
				console.log("登录")
				console.log(this.loginForm)
				this.login();
				
			},
			//刷新验证码
			getCode(){
				this.getCodeImg();
			},
			//忘记密码
			forget(){
				uni.showModal({
				    content: '请联系管理员找回密码',
				    success: function (res) {
				        if (res.confirm) {
							
				        } else if (res.cancel) {
							
				        }
				    }
				});
			}
		}
	}
</script>

<style scoped lang="scss">
	
	.title{
		background-color: #E12A2A;	
		height: 326rpx;
		border-radius: 0px 0px 50.5px 50.5px;
		color:#fff;
		display:flex;
		justify-content: center;
		image{
			margin:120rpx 0;
			height: 160rpx;
			width: 160rpx;
		}
	}
	.up_view{
		text-align: center;
		margin-top: -16rpx;
	}
	.up{
		display: inline-block;
		width: 0;
		height: 0;
		border-right: 12px solid transparent;
		border-left: 12px solid transparent;
		// border-bottom: 12px solid #E12A2A;
		border-top: 13px solid #FF4848;
		margin: 0 auto ;
		// margin: 10px auto;
	}
	.title_login{
		color: #202732;
		fomt-size:32rpx;
		margin: 20rpx 0 60rpx 0;
	}
	
	.dept{
		text{
			dispaly:inline-flex;
		}
	}
	.login{
		text-align: left;
		border: 1px solid rgb(233, 232, 232);
		border-radius: 2.5;
		height: 90rpx;
		line-height: 90rpx;
		margin: 0 34rpx ;
		margin-top: 40rpx;
		font-size: 28rpx;
		color: #0297F4;
		image{
			width: 15px;
			height: 15px;
			display: inline-flex;
			margin-left: 10px;
		}
		input{
			width: 75%;
			margin-left: 10px;
			display: inline-flex;
		}
	}
	.button{
		height: 45px;
		line-height: 50px;
		// width:96%;
		margin: 40rpx 36rpx;
		background-color:#E12A2A ;
		border-radius: 2.5px;
		text-align: center;
		color: #fff;
		font-size: 32rpx;
		// margin: 0 auto;
	}
	
	
	.code_img{
		display: inline-block !important;
		height:38px !important;
		width: 80px !important;
		position: absolute;
		right: 30px !important;
	}
	
</style>
