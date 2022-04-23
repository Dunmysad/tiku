<template>
	<view>
		<!-- <view style="height: 44px;background-color: #F1F3F4;" ></view> -->
		
		<view class="title">
			<image class="back" src="../../../../static/my/back.png" mode="" @click="back()"></image>
			<text>我的考试记录</text>
		</view>
		<view class="answer_block"   >
			<!-- <view></view> -->
			<text class="style" >{{record_result.examRecordBO.examTitle}}</text>
			<view class="answer_point">
				<text>得分：</text>
				<text style="font-weight: bold;" v-if="record_result.examRecordBO.score!=null">{{record_result.examRecordBO.score}}</text>
			</view>
			<view class="answer_time">
				<text>{{record_result.examRecordBO.beginTime}}</text>
				<text style="margin-left: 50rpx;">用时：{{record_result.examRecordBO.spendTime1}}</text>
			</view>
			<view class="answer_time answer_true">
				<text>答对</text>
				<text style="color: blue;margin-left: 10rpx;">{{record_result.rightQuestions}}</text>
				<text>/{{record_result.totalQuestions}}</text>
			</view>
		</view>
		<view class="bottom">
			<view class="judge" v-for="(item,index) in allDetail " :key="index" @click="detail(item)">
				<view v-if="item.isRight" style="background-color:#0044FF ;">
					<image src="../../../../static/my/dui.svg" mode=""></image>
				</view>
				<view v-else style="background-color: #FF4848;">
					<image src="../../../../static/my/cuo.svg" mode=""></image>
				</view>
				<text>{{item.quesSort}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {getRecordDetail,getRecordQuestionDetail} from '../../../../api/record/record.js'
	export default {
		data() {
			return {
				
				record_result:[],
				allDetail:[],
				record_detail:[],
				examInfoId:null,
				examRecordId:null,
				url:''
			}
		},
		methods: {
			
			back(){
				// uni.reLaunch({
				// 	url:'../answer'
				// })
				uni.navigateBack({
					delta:1
				})
			},
			filter(){
				if(this.record_detail){
					console.log(this.record_detail)
					this.record_detail.options = this.record_detail.options.split('|'),
					// this.record_detail.answer = this.record_detail.answer.replace(/[|]/g,"")
					this.record_detail.answer = "c"
					this.record_detail.userAnswer = this.record_detail.userAnswer.replace(/[|]/g,"")
					if(this.record_detail.answer == 'Y'){
						this.record_detail.answer = 'A'
					}else if(this.record_detail.answer == 'N'){
						this.record_detail.answer = 'B'
					}
				}
				
			},
			detail(item){
				var data = item
				data.sort = data.quesSort

				var data1 ={
					examRecordId:this.examRecordId,
					sort :data.sort,
					examDetailId:item.examDetailId
				}
				getRecordQuestionDetail(data1).then(res=>{
					this.details = res.data
					this.record_detail = this.details
					this.filter()
					console.log(this.details ,'this.details ')
					console.log(this.record_detail ,'this.record_detail ')
					var textObj = encodeURIComponent(JSON.stringify(this.record_detail))
					var alldata = encodeURIComponent(JSON.stringify(this.allDetail))
					if(textObj.indexOf('%')>-1){
						textObj = textObj.replace(/%/g, '%25')
					}
					if(alldata.indexOf('%')>-1){
						alldata =  alldata.replace(/%/g, '%25')
					}else {
						return alldata
					}
					console.log("执行到该步骤")
					
					uni.navigateTo({
						url:'../record_detail/record_detail?key='+data.sort+'&&recordId='+this.examRecordId+'&&data='+textObj+'&&alldata='+ alldata
					})
				})
			},
			getRecordDetail(data){
				getRecordDetail(data).then(res=>{
					this.allDetail = res.data
				})
			}
			
		},
		//接收答题事件页面传递的参数,获取到相应的成绩拼接
		onLoad:function(option){
			this.record_result = JSON.parse(decodeURIComponent(option.data))
			if(this.record_result.examRecordBO){
				let data  = this.record_result.examRecordBO
				// consoel.log(data,'this.record_result.examRecordBO')
				this.examRecordId = data.examRecordId
				this.examDetailId = data.examDetailId
				this.getRecordDetail(data)
			}
			
		}
	}
</script>

<style scoped lang="scss">
	
	
	.title{
		background-color: #FF4848;	
		height: 326rpx;
		border-radius: 0px 0px 50.5px 50.5px;
		color:#fff;
		// text-align: center;
		text{
			line-height: 167rpx;
		}
		.back{
			display: inline-block;
			// padding-top: 30rpx;
			height: 20px;
			width: 24px;
			float: left;
			margin: 60rpx 40rpx;
		}
	}
	.answer_block{
		border-radius: 4.5px;
		// box-shadow: 0, 0, 2px, 2px, #77777;
		box-shadow: 0px 0px 2px 1px #DEE1E6;
		margin: -80rpx 40rpx;
		height: 204rpx;
		position: relative;
		background-color: #fff;
	}
	.style{
		background-color: #F7CFCF;
		color:#f52626;
		font-size:22rpx;
		// margin-left: 79.6%;
		float: right;
		display: inline-block;
		padding: 5rpx 20rpx;
		
	}
	.answer_point{
		padding: 30rpx 20rpx;
		padding-bottom: 20rpx;
		font-size: 36rpx;
		color: #080808;
	}
	.answer_time{
		padding: 0 20rpx;
		padding-bottom: 20rpx;
		color: #666565;
		font-size: 24rpx;
	}
	
	.bottom{
		width: 100%;
		display: flex;
		flex-wrap: wrap;
		justify-content:center;
		margin: 160rpx auto;
		text-align: center;
		.judge{
			width: 100rpx;
			height: 100rpx;
			background-color: #F0F0F0;
			border-radius: 2.5;
			margin: 16rpx;
			// line-height: 100rpx;
			view{
				width: 36rpx;
				height: 36rpx;
				color: #fff;
				text-align: center;
				line-height: 36rpx;
				image{
					width: 24rpx;
					height: 24rpx;
				}
			}
		}
	}
</style>
