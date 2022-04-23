<template>
	<view class="home">
		<!-- 图标 -->
		<view class="searchBar">
			<!-- <span class="iconfont icon-shejiao_aixinzhijia_"></span> -->
			<image class="icon-shejiao" src="../../static/tabbar/home-filling.png"></image>
			<span class="tit">学习系统</span>
		</view>
		<!-- 轮播图 -->
		<view class="swipers">
			<swiper indicator-dots autoplay circular interval="2000">
				<swiper-item v-for="(item,index) in swipers" :key="item.id" @click="toStudyDetail(item)">
					<img :src="item.picPath" referrerPolicy="no-referrer" @error="imageError($event,index)" style="width: 100%;height: 100%;" />
				</swiper-item>
			</swiper>
		</view>
		<!-- 导航栏 -->
		<view class="nav">
			<view class="nav_item" v-for="(item,index) in navs" :key="index" @click="toStudy(item)">
				<view :class="item.icon">
					<img :src="item.picPath" referrerPolicy="no-referrer" style="width: 100%;height: 100%;" />
				</view>
				<text>{{item.name}}</text>
			</view>
		</view>
		<!-- 考试入口 -->
		<view class="goExam" @click="toExam()">
			<view class="goTitle">在线考试入口</view>
			<view class="go">GO</view>
		</view>
		<!-- 选项卡 -->
		<view class="cards">
		  <view class="cards-view">
		    <view class="cards-item " :class="start? 'start':'otherStart'" v-for="item in cards" @click="cut(item.title)">
		      <view class="title">{{item.title}}</view>
		    </view>
		  </view>
		</view>
		<!-- <case :final="final" :title="title" :type="type"></case> -->
		<case  :title="title" :onReach= 'onReach'></case>
	</view>
</template>

<script>
	import Home from '../../api/home/home.js'
	import request from '../../utils/request.js'
	import Config from '../../setting.js';
	import Case from '../../components/index/case.vue'
	import {getAllCase} from '@/api/case/case.js'
	import {getStudyData} from '@/api/study/study.js'
	import {getSafeTypeName} from '@/api/pratice/pratice.js'
	import baseUrl from '@/utils/env.js'
	export default {
		name: 'Home',
		data() {
			return {
				swipers: [],
				navs: [],
				cards: [
					{title: '今日推荐'},
					{title: '经典案例'}
				],
				title:'今日推荐',
				start:true,
				onReach:0,
	
			}
		},
		components: {
			Case
		},
		onLoad(option) {
			
			this.title = '今日推荐'
			this.getLunBo()
			this.frontAll()
			
			//分享
			wx.showShareMenu({
				withShareTicket:true,
				//设置下方的Menus菜单，才能够让发送给朋友与分享到朋友圈两个按钮可以点击
				menus:["shareAppMessage","shareTimeline"]
			})
			
		},
		
		methods: {
			imageError(e,index){
				this.swipers[index].picPath = '../../static/fonts/zanwu.png'
			},
			
			//发送给朋友
			onShareAppMessage(res) {
				// 此处的distSource为分享者的部分信息，需要传递给其他人
				let distSource = uni.getStorageSync('distSource');
				if (distSource) {
					return {
						title: '欢迎使用学习系统',
						type: 0,
						path: '/pages/index/index?id=' + distSource,
						summary: "",
						imageUrl: "https://cloudsafe.ruanzhuinfo.com/static/img/newLogo.50a1bf42.png"
					}
				}
			},
			//分享到朋友圈
			onShareTimeline(res) {
				let distSource = uni.getStorageSync('distSource');
				if (distSource) {
					return {
						title: '欢迎使用学习系统',
						type: 0,
						query: 'id=' + distSource,
						summary: "",
						imageUrl: "https://cloudsafe.ruanzhuinfo.com/static/img/newLogo.50a1bf42.png"
					}
				}
			},
			//下拉加载
			onReachBottom(){
				this.onReach = this.onReach+1
			
			},
			
			//点击切换
			cut(title){
				if(title =="今日推荐"){
					this.start = true
					this.title = title
				}
				if(title =='经典案例'){
					this.start = false
					this.title = title
				}
			},
			// 轮播图
			getLunBo(){
				Home.slide().then(res=>{
					this.swipers = res.data.records
					console.log(this.swipers,'swipers')
					
					for(var i in this.swipers){
						this.swipers[i].picPath = getApp().globalData.testUrl.replace('api','api/file/download/')+this.swipers[i].picPath
					}
				})
			},
			// 图标
			frontAll(){
				Home.frontAll().then(res=>{
					this.navs = res.data
					for(var i in this.navs){
						this.navs[i].picPath = getApp().globalData.testUrl.replace('api','api/file/download/')+this.navs[i].picPath
					}
				})
			},
			// 轮播图跳转进入学习详情
			toStudyDetail(item){
				let params = {
					id : item.studyDataId
				}
				Home.studyFront(params).then(res=>{
					console.log(res.data,'data')
					if(res.code === 0){
						uni.navigateTo({
							url: '../study-info/study-info?id='+item.studyDataId+'&fromLunbo='+true
						})
					}
				})
			},
			// 跳转进入考试
			toExam(){
				uni.switchTab({
					url: '../exam/exam'
				})
			},
			// 跳转至当前安全类别的学习页面
			toStudy(item){
				uni.navigateTo({
					url: '../study/study?study='+'&name='+item.name+'&safeTypeId='+item.id
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.home{
		box-sizing: content-box;
		margin: 0 20rpx;
		padding-bottom:60px;
		.searchBar{
			width: 100%;
			padding-top: 70rpx;
			padding-bottom: 20rpx;
			.icon-shejiao{
				width: 50rpx;
				height: 55rpx;
			}
			.tit{
				padding-left: 20rpx;
				font-size: 35rpx;
				font-weight: 600;
				color: #122965;
				position: relative;
				top: -10rpx;
			}
		}
		.swipers{
			border-radius: 10rpx;
			overflow: hidden;
			swiper{
				height: 300rpx;
				width: 710rpx;
				image{
					width: 100%;
					height: 100%;
				}
			}
		}
		.nav{
			display: flex;
			flex-wrap: wrap;
			.nav_item{
				width: 25%;
				text-align: center;
				view{
					width: 100rpx;
					height: 100rpx;
					line-height: 120rpx;
					color: #fff;
					font-size: 40rpx;
					border-radius: 60rpx;
					margin: 10px auto;
					border-radius: 50%;
				}
				text{
					font-size: 15px;
				}
			}
		}
		.goExam{
			margin-top: 30rpx;
			width: 100%;
			height: 120rpx;
			border-radius: 60rpx;
			display: flex;
			justify-content: space-around;
			line-height: 120rpx;
			background: url(https://img-blog.csdnimg.cn/0e92b8d1f27b4ce581f4f7d2f40f0dd1.jpg) no-repeat;
			background-size: 101% 124rpx;
			.goTitle{
				color: #fff;
			}
			.go{
				width: 120rpx;
				height: 60rpx;
				line-height: 60rpx;
				text-align: center;
				border-radius: 30rpx;
				position: relative;
				top: 30rpx;
				background-color: #fdbb53;
				color: #fff;
				font-weight: 700;
			}
		}
		.cards{
			width: 355rpx;
			white-space:nowrap;
			.cards-view{
				display: flex;
				.cards-item{
					width: 560rpx;
					height: 110rpx;
					line-height: 110rpx;
					padding: 0 20rpx;
					color: #333;
					// font-weight: 500;
					position:relative;
					.title{
						font-size: 30rpx;
					}
				}
				.cards-item:hover{
					color: #000;
				}
			}
		}
	}
		
	.start:first-of-type::after{
			content: ""; 
			position: absolute; 
			left: 35%; 
			bottom: 0; 
			width: 30%; 
			height: 10rpx; 
			border-bottom: 10rpx solid 	#F52626; 
			border-radius: 50rpx;
			box-sizing: border-box;
	}
	.otherStart:last-of-type::after{
			content: ""; 
			position: absolute; 
			left: 35%; 
			bottom: 0; 
			width: 30%; 
			height: 10rpx; 
			border-bottom: 10rpx solid 	#F52626; 
			border-radius: 50rpx;
			box-sizing: border-box;
	}
	
</style>
