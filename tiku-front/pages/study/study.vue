<template>
	<view class="study">
		<view class="searchBar">
			<ToIndex :url='url' class="toIndex"></ToIndex>
			<view class="navTitle">{{name}}</view>
		</view>
		<!-- 内容 -->
		<view class="contents">
			<view class="content-item" :class="{'contentVideo':item.dataType!==0}" @click="toStudyInfo(item)" v-for="(item,index) in contents" :key="index">
				<view class="title">{{item.title}}</view>
				<view class="cont" v-if="item.dataType===0">
					<view class="text" v-html="item.content"></view>
					<view class="video" v-if="item.coverPicLink.length!==0">
						<img class="videoCont" @error="imageError($event,index)" :src="item.coverPicLink[0]"></img>
					</view>
				</view>
				<view class="cont" v-if="item.dataType===1">
					<view class="noText">
						<img class="noContVideo" @error="imageError($event,index)" :src="item.coverPicLink[0]" />
					</view>
				</view>
				<view class="cont" v-if="item.dataType===2">
					<view class="noText">
						<img class="noContVideo" @error="imageError($event,index)" :src="item.coverPicLink[0]" />
						<view class="bofang">
							<img class="bofangIcon" src="../../static/fonts/bofang.png" />
						</view>
					</view>
				</view>
				<!-- 播放 -->
				<view class="applaud">
					<view v-if="item.dataType===2">{{item.pageView}}播放</view>
					<view v-else>{{item.pageView}}赞同</view>
				</view>
			</view>
		</view>
		<view class="interval" v-if="flag">------我是有底线的------</view>
	</view>
</template>

<script>
	import ToIndex from "../../components/to-index-page/to-index-page.vue"
	import Home from '../../api/home/home.js'
	import baseUrl from '@/utils/env.js'
	export default{
		data(){
			return {
				contents: [],
				name: '',
				hasVideo: false,
				hasImage: false,
				url: '/pages/index/index',
				// 当前页数
				current: 1,
				flag: false,
				// 案例总数
				total: 0,
				safeTypeId: 0
			}
		},
		components:{
			ToIndex
		},
		onLoad(option) {
			this.name = option.name
			this.safeTypeId = option.safeTypeId
			this.getStudyList()
		},
		methods: {
			imageError(e,index){
				console.log(this.contents[index].coverPicLink[0],'this.contents[index].coverPicLink[0]')
				this.contents[index].coverPicLink[0] = '../../static/fonts/zanwu.png'
				console.log(this.contents[index].coverPicLink[0],'this.contents[index].coverPicLink[0]222')
			},
			// 将图片和视频都加上 http://39.96.41.35/resource/
			// 图片拼接 /api/file/download/
			filter(data){
				data.map(item=>{
					item.coverPicLink = item.coverPicLink.split(',')
					item.coverPicLink = item.coverPicLink.filter(res=> res!=='' )
					if(item.coverPicLink.length!==0){
						item.coverPicLink = item.coverPicLink.map( res=> getApp().globalData.testUrl.replace('api','api/file/download/')+res )
					}
				})
			},
			// 跳转进入学习详情
			toStudyInfo(item){
				uni.navigateTo({
					url: '../study-info/study-info?id='+item.id+'&fromLunbo='+false
				})
			},
			// 调用接口
			getStudyList(callBack){
				let params = {
					safeTypeId: this.safeTypeId,
					current: this.current
				}
				Home.studyData(params).then(res=>{
					this.total = res.data.total
					this.filter(res.data.records);
					this.contents = [...this.contents, ...res.data.records]
					console.log(this.contents,'this.contents')
					// 关闭下拉刷新回调函数
					callBack && callBack()
				})
			}
		},
		// 触底加载
		onReachBottom() {
			if(this.contents.length>=this.total){
				this.flag = true
			}else{
				this.current++
				this.getStudyList()
			}
		},
		// 下拉刷新
		onPullDownRefresh () {
			this.current = 1
			this.contents = []
			this.flag = false
			this.getStudyList(()=>{
				uni.stopPullDownRefresh();
			})
		}
	}
</script>

<style lang="scss" scoped>
	.study{
		.searchBar{
			width: 100%;
			height: 80rpx;
			padding-top: 60rpx;
			position: fixed;
			top: 0;
			background-color: #fff;
			.toIndex{
				display: inline-block;
				width: 15%;
			}
			.navTitle{
				display: inline-block;
				width: 70%;
				text-align: center;
				font-weight: 700;
			}
		}
		.contents{
			margin-top: 100rpx;
			.content-item{
				// height: 340rpx;
				padding: 10px 15px 0 15px;
				border-bottom: 4rpx solid rgb(218, 218, 218);
				box-sizing: border-box;
				.title{
					color: #000;
					font-weight: 700;
					padding-top: 20rpx;
					padding-bottom: 20rpx;
					// height: 80rpx;
					// line-height: 80rpx;
					// padding-left: 20rpx;
				}
				.cont{
					display: flex;
					// height: 160rpx;
					// padding-left: 10rpx;
					// padding-right: 10rpx;
					.text{
						height: 160rpx;
						line-height: 40rpx;
						font-size: 28rpx;
						color: rgb(51, 51, 51);
						letter-spacing: 4rpx;
						box-sizing: border-box;
						overflow: hidden;
						text-overflow: ellipsis;
					}
					.text{
						width: 100%;
						// 省略号 ...
						display: -webkit-box;
						-webkit-line-clamp: 4;
						-webkit-box-orient: vertical;
					}
					.video{
						height: 155rpx;
						width: 45%;
						border-radius: 4rpx;
						overflow: hidden;
						.videoCont{
							width: 100%;
							height: 100%; 
							object-fit: fill
						}
					}
					.noText{
						height: 400rpx;
						width: 100%;
						border-radius: 4rpx;
						overflow: hidden;
						position: relative;
						.noContVideo{
							width: 100%;
							height: 100%; 
							object-fit: fill
						}
						.bofang{
							position: absolute;
							top: 120rpx;
							left: 264rpx;
							width: 160rpx;
							height: 160rpx;
							.bofangIcon{
								width: 100%;
								height: 100%;
								object-fit: fill
							}
						}
					}
					
				}
			}
			// 赞同
			.applaud{
				font-size: 28rpx;
				color: rgb(51, 51, 51);
				padding: 20rpx 0 20rpx 0;
			}
			// 当是视频时，高度改变
			.contentVideo{
				// height: 580rpx;
			}
			
		}
		.interval{
			font-size: 25rpx;
			width: 100%;
			height: 50px;
			line-height: 50px;
			text-align: center;
		}
	}
</style>
