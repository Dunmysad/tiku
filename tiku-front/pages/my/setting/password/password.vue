<template>
	<view >
		<view class="explain">原密码</view>
		<input class="password" type="text"  password placeholder="输入原密码" v-model="oldPass" maxlength=20/>
		
		<view class="explain">新密码(6-20位密码)</view>
		<input class="password" type="text" password placeholder="输入新的密码" v-model="newPass" maxlength=20/>
		
		<view class="explain">确认密码</view>
		<input class="password" type="text" password placeholder="再次输入密码" v-model='againPass' maxlength=20/>
		<text class="password_explain"></text>
		
		<view class="finish" @click="updatePassWord">完成</view>
		<uni-popup ref="popup" type="type" :animation="false" duration="1000" :maskClick="true" >
			<view style="background-color: #fff;padding: 15px;border-radius: 4%;width:150px;height:45px;line-height: 45px;text-align: center;">
				修改密码成功！
			</view>
		</uni-popup>
	</view>
</template>

<script> 
	import {updatePass,logout} from '../../../../api/my/my.js'
	const publicKey = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD\n' +
	  '2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ=='
	const Encrypt = require('@/utils/jsencrypt.js')
	let cryptFirst = new Encrypt.JSEncrypt()
	cryptFirst.setPrivateKey(publicKey)
	export default {
		data() {
			return {
				newPass:'',
				oldPass:'',
				againPass:''
			}
		},
		methods: {
			
		
			finish(){
				this.$refs.popup.open(); //提示信息
			},
			reSet(){
				this.newPass ='',
				this.oldPass = '',
				this.againPass =''
			},
			updatePassWord(){
				console.log("调用该方法")
				if(this.newPass==''||this.oldPass=='' ||this.againPass ==''){
					uni.showToast({
						icon: "none",
						title: "请确认密码输入",
						mask: true
					});
					this.reSet();
				}else if(this.newPass.length<6||this.newPass.length>20){
					uni.showToast({
						icon: "none",
						title: "密码不符合要求",
						mask: true
					})
					this.reSet();
				}else if(this.newPass != this.againPass){
					uni.showToast({
						icon: "none",
						title: "两次输入密码不一致",
						mask: true
					})
					this.reSet();
				}else {
					var data ={
						oldPass:cryptFirst.encrypt(this.oldPass),
						newPass:cryptFirst.encrypt(this.newPass)
					}
					console.log(data,'data')
					updatePass(data).then(res=>{
						console.log(res,'res.data')
						if(res.code == 0){
							uni.showToast({
								icon: "none",
								title: "修改成功",
								mask: true
							})
							this.logout()
						}else {
							this.finish()
							// uni.showToast({
							// 	icon: "none",
							// 	title:res.message,
							// 	mask: true
							// })
						}
						this.reSet();
					})
				}					
			},
			logout(){
				logout().then(res=>{
					// console.log(res,"退出登录")
					if(res.code == 0){
						// console.log("退出登录1111111111111")
						uni.navigateTo({
							url:'../../../login/login'
						})
					}
				})
			}
			
			
		},
		
		
	}
</script>

<style scoped lang="scss">
	.explain{
		height:44px;
		line-height: 44px;
		margin-left: 10px;
		font-size:15px;
	}
	.password{
		height: 55px;
		line-height: 55px;
		padding-left: 10px;
		background-color: #F8F8F8;
	}
	input{
		border:1px solid #E6E6E6;
	}
	.password_explain{
		padding-left: 10px;
		display:flex;
		margin-top:10px;
		font-size:15px;
		color:grey
	}
	.finish{
		margin:20px auto;
		height: 44px;
		background-color:#F8F8F8 ;
		text-align: center;
		line-height: 44px;
		border-radius: 4%;
	}
</style>
