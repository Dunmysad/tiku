<template>
	<view class="exam">
		<view class="title">
			<text>教育考试</text>
		</view>
		<view class="style" >
			<view class="style_exam" style="background-color: #6374DF;" @click="skipPratice()">
				<image src="../../static/exam/b2.png" mode=""></image>
				<view class="style_view">
					<view class="styel_title">题库练习</view>
					<text>参加考试前，可进入题库学习及模拟考试</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {getInfo} from '@/api/my/my.js'
	import {getARecord,getAllRecord,getRecordDetail,getRocordById} from '@/api/record/record.js'
	import {getHignScore} from '@/api/pratice/pratice.js'
	import {timestampToTime} from '@/utils/formatDate.js'
	import  Axios from '@/utils/request.js'
	export default{
		data(){
			return{
				// name:'王XX'
				// isLogin:true,
				exam:null,
				userId:null,
				scoreRecord:null,
				data:{
					examInfoId:187,
					examRecordId:254
				}
			}
		},
		onShow(){
			console.log(uni.getStorageSync("token"),'uni.getStorageSync("token")')
			if(uni.getStorageSync("token")){
				// this.getInfo();
				// this.getScore()
			}
		},
		methods:{
			//获取个人信息
			// getInfo(){
			// 	getInfo().then(res=>{
			// 		this.userId  = res.data.user.id
			// 		console.log(res.data,'res.data')
					
			// 	})
			// },
	
			//对时间处理
			formateTime(data) {
			    if(data!==null){
			        let h = parseInt(data / 3600)
			        let minute = parseInt(data / 60 % 60)
			        let second = Math.ceil(data % 60)  
			        const formatSecond = second > 59 ? 59 : second
			        return `${h < 10 ? '0' + h : h}:${minute < 10 ? '0' + minute : minute}:${formatSecond < 10 ? '0' + formatSecond : formatSecond}`
			    }
			},
			//时间戳转换
			filter(){
				if(this.exam){
					this.exam.examRecordBO.beginTime = timestampToTime(this.exam.examRecordBO.beginTime)
					this.exam.examRecordBO.spendTime1 = this.formateTime(this.exam.examRecordBO.spendTime)
				}
			},
			
			//跳转到练习
			skipPratice(){
				uni.navigateTo({
					url: 'pratice_index/pratice_index'
				})
			},
			
		
			//跳转到登录页面
			toLogin(){
				if(!uni.getStorageSync("token")){
					uni.navigateTo({
						url: '/pages/login/login'
					});
				}
			}
		},
		
	}
</script>

<style scoped lang="scss">
	
	.exam{
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
			margin-top: -180rpx;
			.style_exam{
				height: 230rpx;
				// background-color: #1ED3CC;
				margin: 15px;
				color: #fff;
				position: relative;
				image{
					// display: inline-block;
					position: absolute;
					width:150rpx;
					height: 150rpx;
					margin: auto 40rpx;
					top: 40rpx;
				}
				.style_view{
					margin: auto 40rpx auto 210rpx;
					padding-top:40rpx ;
					text{
						font-size: 24rpx;
						line-height: 34rpx;
					}
					.styel_title{
						display:block;
						font-size: 36rpx;
					}
				}	
				
			}
		}
	}
</style>
