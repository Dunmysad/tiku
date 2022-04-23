<template>
	<view class="study-info" v-if="studyInfo!==null">
		<view class="searchBar">
			<view class="toIndexIcon" @click="toIndex()">
				<image class="icon-fanhui" src="../../static/fonts/back.png"></image>
			</view>
			<view class="navTitle">{{name}}</view>
		</view>
		<view class="containt">
			<view class="head">
				<view class="title">{{studyInfo.title}}</view>
				<view class="infor">
					<span class="date">{{studyInfo.createTime}} · </span>
					<span class="read move"> {{ studyInfo.pageView}}阅读 · </span>
					<span class="like move"> {{ liveCount}}点赞</span>
				</view>
			</view>
			<view class="content">
				<view class="videoInfo" v-if="studyInfo.dataType===0">
					<view class="video" v-for="(item,index) in studyInfo.coverPicLink" :key="index">
						<img class="videoCont" @error="imageError($event,index)" :src="item"></img>
					</view>
					<view v-if="studyInfo.content!==''" v-html="studyInfo.content"></view>
				</view>
				<view class="videoInfo" v-if="studyInfo.dataType===1">
					<view class="video" v-for="(item,index) in studyInfo.coverPicLink" :key="index">
						<img class="videoCont" @error="imageError($event,index)" :src="item"></img>
					</view>
					<view v-if="studyInfo.content!==''" v-html="studyInfo.content"></view>
				</view>
				<view class="videoInfo" v-if="studyInfo.dataType===2">
					<view class="video" v-for="(item,index) in studyInfo.videoPath" :key="index">
						<video :src="item"></video>
					</view>
					<view v-if="studyInfo.content!==''" v-html="studyInfo.content"></view>
				</view>
			</view>
		</view>
		<view class="footer">
			<view class="give giveLive" @click="giveLive">
				<image class="icon-zanpress" src="../../static/fonts/zan.png" v-if="clickLike"></image>
				<image class="icon-dianzan-active" src="../../static/fonts/zan-filter.png" v-else></image>
				<span>{{liveCount}}</span>
			</view>
			<view class="give giveTread" @click="giveTread">
				<image class="icon-diancai" src="../../static/fonts/cai.png" v-if="clickTread"></image>
				<image class="icon-diancai-active" src="../../static/fonts/cai-filter.png" v-else></image>
				<span>{{treadCount}}</span>
			</view>
		</view>
		
	</view>
</template>

<script>
	import moment from "moment";
	import Home from '../../api/home/home.js'
	import baseUrl from '@/utils/env.js'
	export default {
		data() {
			return {
				// 不点赞为true
				clickLike: true,
				// 不点踩为true
				clickTread: true,
				studyInfo: null,
				liveCount: 0,
				treadCount: 0,
				name: '',
				fromLunbo: ''
			}
		},
		onLoad(option) {
			// this.studyInfo = JSON.parse(decodeURIComponent(option.studyInfo))
			let params = {
				id : option.id
			}
			Home.studyFront(params).then(res=>{
				console.log(res.data,'data')
				if(res.code === 0){
					this.studyInfo = res.data
					this.studyInfo.createTime = moment.unix(this.studyInfo.createTime/1000).format("YYYY-MM-DD")
					// console.log(this.studyInfo,'this.studyInfo11111')
					if(option.fromLunbo===false){
						this.name = this.studyInfo.safeTypeName
					}else{
						this.name = this.studyInfo.safeTypeName
					}
					
					this.fromLunbo = option.fromLunbo
					// 处理富文本中的图片
					if(this.studyInfo.content!==''&&this.studyInfo.content!==null&&this.studyInfo.content){
						const regex = new RegExp('<img','gi');
						this.studyInfo.content = this.studyInfo.content.replace(regex,`<img style="max-width: 100%;"`);
						
						const regexP = new RegExp('<p','gi');
						this.studyInfo.content = this.studyInfo.content.replace(regexP,`<p style="text-align: left;text-indent: 2em;"`);
					}
					// 赞了赞
					this.liveCount = this.studyInfo.tags
					this.clickLike = !this.studyInfo.isTags
					// 踩了踩
					this.treadCount = this.studyInfo.dislike
					this.clickTread = !this.studyInfo.isDislike
					
					this.filter();
					// 播放量加一
					// let datas = {
					// 	id: res.data.id,
					// 	count: res.data.pageView+1
					// }
					// Home.read(datas).then(val=>{
						
					// })
				}
			});
		},
		methods: {
			imageError(e,index){
				console.log("1211111",this.studyInfo)
				this.studyInfo.coverPicLink[index] = '../../static/fonts/zanwu.png'
				
			},
			filter(){
				if(this.studyInfo.videoPath!==''){
					this.studyInfo.videoPath = this.studyInfo.videoPath.split(',')
					this.studyInfo.videoPath = this.studyInfo.videoPath.filter(res=> res!=='' )
					this.studyInfo.videoPath = this.studyInfo.videoPath.map( res=> getApp().globalData.testUrl.replace('api','api/file/download/')+res )
				}
				
				if(this.studyInfo.coverPicLink!==''){
					this.studyInfo.coverPicLink = this.studyInfo.coverPicLink.split(',')
					this.studyInfo.coverPicLink = this.studyInfo.coverPicLink.filter(res=> res!=='' )
					this.studyInfo.coverPicLink = this.studyInfo.coverPicLink.map( res=> getApp().globalData.testUrl.replace('api','api/file/download/')+res )
				}
			},
			// 点赞
			giveLive(){
				if(this.clickLike===true){
					// 点了一个赞
					if(uni.getStorageSync("token")){
						this.clickLike=false
						this.liveCount=this.liveCount+1
						let params = {
							count: this.liveCount,
							id: this.studyInfo.id,
							isTags: true
						}
						Home.like(params).then(res=>{
							console.log(res,'赞')
						})
					}else{
						uni.showModal({
						    title: '您还未登录，请先登录',
						    // content: '这是一个模态弹窗',
						    success: function (res) {
						        if (res.confirm) {
						            setTimeout(function () {
						            	uni.navigateTo({
						            		url: '/pages/login/login'
						            	});
						            }, 1000);
						        } else if (res.cancel) {
						            console.log('用户点击取消');
						        }
						    }
						});
					}
					
				}else{
					// 取消了点赞
					if(uni.getStorageSync("token")){
						this.clickLike=true
						this.liveCount=this.liveCount-1
						let params = {
							count: this.liveCount,
							id: this.studyInfo.id,
							isTags: false
						}
						Home.like(params).then(res=>{
							console.log(res,'赞')
						})
					}else{
							uni.showModal({
							    title: '您还未登录，请先登录',
							    // content: '这是一个模态弹窗',
							    success: function (res) {
							        if (res.confirm) {
							            setTimeout(function () {
							            	uni.navigateTo({
							            		url: '/pages/login/login'
							            	});
							            }, 1000);
							        } else if (res.cancel) {
							            console.log('用户点击取消');
							        }
							    }
							});
						}
					
				}
			},
			// 点踩
			giveTread(){
				if(this.clickTread===true){
					// 点了一个踩
					if(uni.getStorageSync("token")){
						this.clickTread=false
						this.treadCount=this.treadCount+1
						let params = {
							count: this.treadCount,
							id: this.studyInfo.id,
							isDislike: true
						}
						Home.dislike(params).then(res=>{
							console.log(res,'踩')
						})
					}else{
							uni.showModal({
							    title: '您还未登录，请先登录',
							    // content: '这是一个模态弹窗',
							    success: function (res) {
							        if (res.confirm) {
							            setTimeout(function () {
							            	uni.navigateTo({
							            		url: '/pages/login/login'
							            	});
							            }, 1000);
							        } else if (res.cancel) {
							            console.log('用户点击取消');
							        }
							    }
							});
						}
					
				}else{
					// 取消了踩
					if(uni.getStorageSync("token")){
						this.clickTread=true
						this.treadCount=this.treadCount-1
						let params = {
							count: this.treadCount,
							id: this.studyInfo.id,
							isDislike: false
						}
						Home.dislike(params).then(res=>{
							console.log(res,'踩')
						})
					}else{
							uni.showModal({
							    title: '您还未登录，请先登录',
							    // content: '这是一个模态弹窗',
							    success: function (res) {
							        if (res.confirm) {
							            setTimeout(function () {
							            	uni.navigateTo({
							            		url: '/pages/login/login'
							            	});
							            }, 1000);
							        } else if (res.cancel) {
							            console.log('用户点击取消');
							        }
							    }
							});
						}
					
				}
			},
			toIndex(){
				console.log(this.fromLunbo,'this.fromLunbo')
				if(this.fromLunbo==='true'){
					var title = 1
					uni.reLaunch({
						url: '/pages/index/index?id='+title
					})
				}else{
					uni.navigateTo({
					    url: '/pages/study/study?safeTypeId='+this.studyInfo.safeTypeId+'&name='+this.name
					});
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.study-info{
		.searchBar{
			width: 100%;
			height: 80rpx;
			padding-top: 60rpx;
			position: fixed;
			top: 0;
			z-index: 999;
			background-color: #fff;
			.toIndexIcon{
				display: inline-block;
				width: 100rpx;
				height: 60rpx;
				text-align: center;
				line-height: 60rpx;
				.icon-fanhui{
					// font-size: 45rpx;
					width: 40rpx;
					height: 40rpx;
					position: relative;
					top: 8rpx;
				}
			}
			.navTitle{
				display: inline-block;
				width: 70%;
				text-align: center;
				font-weight: 700;
			}
		}
		.containt{
			margin: 160rpx 30rpx 40rpx;
			.head{
				.title{
					font-size: 36rpx;
					font-weight: 700;
					margin-bottom: 20rpx;
				}
				.infor{
					font-size: 28rpx;
					color: #b1afaf;
					.move{
						display: inline-block;
						padding-left: 10rpx;
					}
				}
			}
			.content{
				.videoInfo{
					margin-top: 40rpx;
					.video{
						width: 690rpx;
						height: 500rpx;
						margin-bottom: 40rpx;
						video{
							width: 100%;
							height: 100%;
							object-fit: fill;
						}
						img{
							width: 100%;
							height: 100%;
							object-fit: fill;
						}
					}
				}
			}
		}
		.footer{
			display: flex;
			justify-content: center;
			padding-top: 40rpx;
			padding-bottom: 100rpx;
			.give{
				width: 200rpx;
				height: 70rpx;
				line-height: 70rpx;
				border-radius: 35rpx;
				border: 3rpx solid #d2d1d1;
				display: flex;
				justify-content: center;
				margin: 10rpx 20rpx 10rpx 20rpx;
			}
			.icon-zanpress{
				width: 60rpx;
				height: 60rpx;
			}
			.icon-dianzan-active{
				width: 60rpx;
				height: 60rpx;
			}
			.icon-diancai{
				width: 60rpx;
				height: 60rpx;
				position: relative;
				top: 6rpx;
			}
			.icon-diancai-active{
				width: 60rpx;
				height: 60rpx;
				position: relative;
				top: 6rpx;
			}
		}
	}
</style>
