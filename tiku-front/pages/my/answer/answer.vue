<template>
	<view >
		<view class="answer" v-if="answer_result.length>0">
			<!-- <view v-for="(item,index) in answer " :key="index" @click="toRecord(index)">
				{{item.time}}
			</view> -->
			<view class="answer_block"  v-for="(item,index) in answer_result" :key="index"  >
				<!-- <view></view> -->
				<image  src="../../../static/my/zuojiantou1.png" mode="" @click="toRecord(item)"></image>
				<text class="style" >{{item.examRecordBO.examTitle}}</text>
				<view class="answer_point">
					<text>得分：</text>
					<text style="font-weight: bold;">{{item.examRecordBO.score}}</text>
				</view>
				<view class="answer_time">
					<text >{{item.examRecordBO.beginTime}}</text>
					<text style="margin-left: 50rpx;" >用时：{{item.examRecordBO.spendTime1}}</text>
				</view>
				<view class="answer_time answer_true">
					<text>答对</text>
					<text style="color: blue;margin-left: 10rpx;">{{item.rightQuestions}}</text>
					<text>/{{item.totalQuestions}}</text>
				</view>
			</view>
			
		</view>
		<view class="" v-else style="text-align: center;">
			<text>-----暂无数据-----</text>
		</view>
		<view v-if='isbottom&&answer_result.length>0' style="text-align: center;margin-bottom: 40rpx;">
			<text>-----已经到达底部-----</text>
		</view>
	</view>
</template>

<script>
	import {getARecord} from '../../../api/record/record.js'
	import {timestampToTime,timestampToM} from '../../../utils/formatDate.js'
	export default {
		data() {
			return {
				answer_result:[],
				data:{
					current:1,
					size:10
				},
				start:[],
				isbottom:false
			}
		},
		methods: {
			//下拉加载
			onReachBottom(){
				if(!this.isbottom){
					var judgeNum2 = this.data
					judgeNum2.current = judgeNum2.current+1
					this.getRecord(judgeNum2)
				}
			},
			getRecord(data){
				getARecord(data).then(res=>{
					console.log(res.data,'分页部门')
					this.start = res.data.records
					console.log(this.start,"this.result..99999answer")
					if(this.start !=null&&this.start.length>0){
						this.start.forEach(item=>{
							this.answer_result.push(item)
						})
					}else {
						this.isbottom = true
						console.log(this.isbottom,'this.isbottom')
					}
					this.filter()
					// this.start = this.answer_result
				})
			},
			toRecord(item){
				var textObj = encodeURIComponent(JSON.stringify(item))
				// console.log(textObj,'textObj')
				// console.log(index,"传输的数据")
				uni.navigateTo({
					url:'record/record?data='+textObj
				})
			},
			//时间戳转换
			filter(){
				if(this.answer_result){
					this.answer_result.forEach(item=>{
						item.examRecordBO.beginTime = timestampToTime(item.examRecordBO.beginTime)
						item.examRecordBO.spendTime1 = this.formateTime(item.examRecordBO.spendTime)
					})
				}
			},
			formateTime(data) {
			    if(data!==null){
			        let h = parseInt(data / 3600)
			        let minute = parseInt(data / 60 % 60)
			        let second = Math.ceil(data % 60)  
			        const formatSecond = second > 59 ? 59 : second
			        return `${h < 10 ? '0' + h : h}:${minute < 10 ? '0' + minute : minute}:${formatSecond < 10 ? '0' + formatSecond : formatSecond}`
			    }
			},
		},
		onLoad:function(option){
			this.getRecord(this.data)
		}
	}
</script>

<style scoped lang="scss">
	.answer{
		.answer_block{
			border-radius: 1.5px;
			// box-shadow: 0, 0, 2px, 2px, #77777;
			box-shadow: 0px 0px 2px 1px #DEE1E6;
			margin: 40rpx 40rpx;
			height: 204rpx;
			position: relative;
			image{
				width: 50rpx;
				height: 50rpx;
				position: absolute;
				top: 45%;
				right: 5%;
			}
			// line-height: 204rpx;
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
		
	}
</style>
