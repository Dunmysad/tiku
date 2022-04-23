<template>
	<view class="pratice_index">
		<view class="title" >
			
		</view>
		<view class="top" style="height:160rpx;" >
			
			<image class="back" src="../../../static/my/back.png" mode="" @click="toBack()"></image>
			<image src="../../../static/exam/bg.jpg" mode=""></image>
		</view>
		<view class="top_text">
			<text style="font-size: 52rpx;" >大学生知识题库</text>
			<text style="font-size: 40rpx;">每日一练</text>
		</view>
		<view class="bottom">
			<view class="bottom_style" v-for="(item,index) in kinds" :key="item.id" @click=toSkip(item) :style=" 'background-color:'+ color[index]  "  >
				<view style="width: 95px;margin:30rpx 0 0 20rpx;">
					<text style="font-size: 44rpx;">{{item.one}}</text>
					<text >{{item.title}}</text>
				</view>
				<img referrerPolicy="no-referrer" :src='item.repositoryPath' mode="" ></img>
				<view class="x_line">
				</view>
				<view class="c_line">
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {getSafeType,getAllSafeType} from '../../../api/pratice/pratice.js'
	import baseUrl from '@/utils/env.js'
	export default {
		data() {
			return {
				name:'王XX',
				color:[
					"#D04142","#D75D01","#EB3D3C","#F59401","#E27F76","#F6BB05","#DCAC8C","#FDD801"
				],
				kinds:[],
				data:{
					current:1,
					size:10
				}
				
			}
		},
		onLaunch(){
			uni.showTabBar()
		},
		onLoad(){
			this.getAllType()
			// this.getAllSafeType(this.data)
			
		},
		methods: {
			// onReachBottom(){
			// 	// console.log("下拉刷新")
			// 	this.data.surrent = this.data.current+1
			// 	this.getAllSafeType(this.data)
			// },
			async getAllType (){
				const res = await getSafeType()
				this.kinds = res.data
				this.filter()
				
				// console.log(this.kinds,'one')
			},
			
			filter(){
				this.kinds.forEach(item=>{
					item.repositoryPath = getApp().globalData.testUrl.replace('api','api/file/download/')+item.repositoryPath
					//获取标题中的第一个字，和其余部分
					if(item.name!=''){
						item.one = item.name.substr(0,1);
						item.title = item.name.substr(1);
					}
				})
			},
			toBack(){
				uni.reLaunch({
					url:'../exam'
				})
			},
			toSkip(item){
				console.log(uni.getStorageSync("token"),'uni.getStorageSync("token")')
				if(!uni.getStorageSync("token")){
					uni.showModal({
					    title: '您还未登录，请先登录',
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
					            	uni.navigateTo({
					            		url: '/pages/login/login'
					            	});
					            }, 1000);
					        } 
					    }
					});
				}else {
					var textObj = encodeURIComponent(JSON.stringify(item))
					// console.log(index,"传递的数据index")
					uni.navigateTo({
						url:'../pratice/pratice?data='+textObj
						// url:'../pratice/pratice?key='+index
					})
				}
				
			},
			
		}
	}
</script>

<style scoped lang="scss">
	.pratice_index{
		background-color: #F6F6F6;
		min-height: 1310rpx;
		view{
			dispaly:flex;
			// height: 231px;
			font-family: KaiTi_GB2312, KaiTi, STKaiti;
		}
		.title{
			
		}
		.top{
			// height: 188px;
			overflow:hidden;
			position:relative;
			
			image{
				width:123%;
				height:532.8rpx;
				margin-top: -20rpx;
				margin-left: -80rpx;
			}
			.back{
				position: absolute;
				z-index: 999;
				margin: 10px 0 0 15px ;
				height: 20px;
				width: 24px;
				padding-top: 45rpx;
			}
		}
		.top_text{
			// position: absolute;
			z-index: 999;
			height: 100px;
			text-align: center;
			// left:20%;
			// top: 13%;
			// top: 160rpx;
			text{
				display:block;
				color:#EA2A2A;
				font-family: KaiTi_GB2312, KaiTi, STKaiti;
				margin-bottom:20px ;
			}
		}
		
			.bottom{
				background-color: #F6F6F6;
				padding: 25rpx 30rpx;
				display: flex;
				flex-wrap: wrap;
				justify-content: space-around;
				padding: 20rpx 40rpx 60rpx 40rpx;
				.bottom_style{
					width: 300rpx;
					height: 178rpx;
					flex: 0 0 45% ;
					background-color: #D04142;
					border-radius: 6.5px;
					color: #fff;
					position: relative;
					// line-height: 100rpx;
					margin: 10px auto;
					text{
						font-size: 36rpx;
						// display:inline-block;
						letter-spacing: -5rpx;
					}
					.bottom_text{
						// width: 64px;
					}
					img{
						width: 68rpx;
						height: 68rpx;
						display: inline-block;
						position: absolute;
						right: 20rpx;
						top: 30rpx;
					}
				}
			}
			.bottom:after {
			    content:"";
			    width:50%;
			}
		.x_line{
			position: absolute;
			height:2rpx;
			width:52%;
			background-color: #EBEDEE;
			bottom: 41rpx;
			// bottom: 20.5px;
			right: 85rpx;
			opacity: 0.6;
		}
		.c_line{
			position: absolute;
			height:4rpx;
			width:18%;
			background-color: #EBEDEE;
			bottom: 40rpx;
			right: 30rpx;
			opacity: 0.6;
		}
		
	}
</style>
