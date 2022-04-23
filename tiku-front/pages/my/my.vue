<template>
	<view class="my">
		
		<view class="title">
			<text>我的</text>
		</view>
		<view class="style" >
			<view class="style_exam" style="background-color: #fff;">
				<image :src="head_img" mode="" style="border-radius: 50%;"></image>
				<view class="style_view">
					<view class="styel_title">{{user.username}}</view>
					<text v-if="this.user.student&&this.user.student.studentNum">{{user.student.studentNum}}</text>
				</view>
			</view>
		</view>
		<view class="bottom">
			<view class="my_result" @click="toSetting()">
				<image src="../../static/my/shezhi.png" mode=""></image>
				<text>设置</text>
				<image style="float: right;"src="../../static/my/zuojiantou.png" mode="" ></image>
			</view>
		</view>
		<view class="button" v-if="isLogin" @click="toSkip()" >退出登录</view>
		<view class="button" v-else @click="toLogin()" >登录</view>
		<!-- <u-mask :show="show" @click="show = false">
			<view  style="padding-top: 70%;padding-left: 10%;">
				<uni-popup ref="popup" type="dialog">
					<uni-popup-dialog type="warn" title="提示" content="确定退出？" :before-close="true"  @confirm="dialogConfirm" @close="dialogClose" ></uni-popup-dialog>
				</uni-popup>
			</view>
			
		</u-mask> -->
		
	</view>
	
	
</template>

<script>
import {getInfo,logout} from '../../api/my/my.js'
	import baseUrl from '@/utils/env.js';
	import {getHignScore} from '@/api/pratice/pratice.js'
	import  Axios from '@/utils/request.js'
	export default{
		// components:{uMask,uniPop},
		data() {
				return {
					isLogin:false,
					userInfo:{username:null,school:null,judgeUser:null,highPoint:null},
					user:{},
					head_img:'../../static/my/touxiang.png',
					show:false,
					scoreRecord:null
				}
			},
			
			onShow(){
				console.log(uni.getStorageSync("token"),'uni.getStorageSync("token"),my,show')
				if(uni.getStorageSync("token")){
					this.getUser();
					
				}
			},
			methods: {
				// getScore(){
				// 	getHignScore().then(res=>{
				// 		this.scoreRecord = res.data.score
				// 	})
				// },
				getUser(){
					getInfo().then(res=>{
						
						if(res.code == 0){
							this.isLogin = true;
							// this.getScore()
							
						}
						console.log(res,'res')
						this.user = res.data.user
						// if(this.user.student&&this.user.student.score){
						// 	this.scoreRecord = this.user.student.score
						// }
						if(this.user.avatarPath){
							this.head_img = getApp().globalData.testUrl.replace('api','api/file/download/')+this.user.avatarPath
						}
						
					});
					
					
				},
				toLogin(){
					uni.navigateTo({
						url:"../login/login"
					})
				},
				toScore(){
					uni.navigateTo({
						url:"score/score"
					})
				},
				toSetting(){
					uni.navigateTo({
						url:"setting/setting"
					})
				},
				toAboutMy(){
					uni.navigateTo({
						url:"aboutOur/aboutOur"
					})
				},
				toSkip(){
					// this.show= true
					// this.$refs.popup.open();
					uni.showModal({
					    title: '确定退出登录？',
					    // content: '这是一个模态弹窗',
					    success: function (res) {
					        if (res.confirm) {
								uni.setStorageSync('userInfo', null);
								getApp().globalData.userInfo = null
								if (getApp().globalData.isSocketConnect) {
									uni.closeSocket();
									getApp().globalData.isSocketConnect = false;
								}
					            setTimeout(function () {
									logout().then(res=>{
										if(res.code == 0){
											uni.clearStorage()
											Axios.isLogin = false
											uni.navigateTo({
												url:'../login/login'
											})
										}
									})
					            	
					            }, 0);
					        } else if (res.cancel) {
								uni.navigateBack({
									delta: 1
								});
					           
					        }
					    }
					});
				},
				//点击确定
				dialogConfirm() {
					this.$refs.popup.close();
					logout().then(res=>{
						console.log(res,"退出登录")
						if(res.code == 0){
							uni.clearStorage()
							Axios.isLogin = false
							uni.navigateTo({
								url:'../login/login'
							})
						}
					})
				},
				//点击取消
				dialogClose() {
					this.$refs.popup.close();
				}
			}
		}
</script>

<style scoped lang="scss">
	
	.my{
		background-color: #F0F0F0;
		height: 1235rpx;
	}
	.title{
		background-color: #FF4848;	
		height: 326rpx;
		border-radius: 0px 0px 50.5px 50.5px;
		color:#fff;
		text-align: center;
		text{
			line-height: 167rpx;
		}
	}
	.style{
			margin-top: -120rpx;
			.style_exam{
				height: 230rpx;
				border-radius: 4.5px;
				margin: 20rpx;
				text-align: center;
				image{
					width:150rpx;
					height: 150rpx;
					margin-top: -80rpx;
					
				}
				.style_view{
					margin: 0 auto;
					text-align: center;
					padding-top:20rpx ;
					text{
						font-size: 24rpx;
						line-height: 34rpx;
						color:#A9A9A9;
					}
					.styel_title{
						display:block;
						font-size: 32rpx;
						color:#202735;
					}
				}	
				
			}
		}
	.bottom{
		.my_result{
			margin: 20rpx;
			background-color: #fff;
			height: 100rpx;
			border-radius: 4.5px;
			line-height: 100rpx;
			// text-align: center;
			image{
				float: left;
				width: 40rpx;
				height: 40rpx;
				margin: auto 20rpx auto 30rpx;
				// top: 30%;
				padding-top: 30rpx;
			}
		}
	}
	.button{
		height: 100rpx;
		margin: 40rpx 20rpx;
		background-color:#FF4848;
		border-radius: 4.5px;
		text-align: center;
		line-height: 100rpx;
		color:#fff;
		font-size: 28rpx;
		
	}
	.logut{
		// margin: auto auto;
	}
	// #popstyle{
	// 	padding-top: 70%;
	// 	padding-left:10% ;
	// }
</style>
