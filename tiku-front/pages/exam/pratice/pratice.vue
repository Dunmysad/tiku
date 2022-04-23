<template>
	<view class="pratice">
		<view class="top">
			<!-- <text >大学生安全教育题库</text> -->
		</view>
		<view style="background-color: #0066FF;font-size:24rpx;width: 20%;text-align: center;color: #fff;height: 50rpx;line-height: 50rpx;margin: 0 auto;">{{kind}}</view>

		<view class="toAnswer"  v-if="final.length!==0">
			<view class="topic">
				<text>{{questionIndex}}</text>
				<!-- <text v-if="final && final[questionIndex-1].quesType==1">、{{final[questionIndex-1].quesType==1?'【单项选择】':'【多项选择】'}}</text> -->
				<text v-if="final[questionIndex-1].quesType==1">、【单选题】</text>
				<text v-if="final[questionIndex-1].quesType==2">、【多选题】</text>
				<text v-if="final[questionIndex-1].quesType==3">、【判断题】</text>
				<rich-text class="richText" v-if='final[questionIndex-1].quesContent' :nodes="final[questionIndex-1].quesContent"></rich-text>
			</view>


			<view class="topinOptions" v-if="final.length>0">
				<!-- 单选 -->
				<radio-group v-if=" final[questionIndex-1].quesType==1" @change="radioChange">
					<view v-for="(item, index) in final[questionIndex-1].options" :key="index">
						<view class="options" @click="redioCheck(index) ">
							<radio :disabled="isDisabled" :value="alphabet[index]" :color="color" :checked="alphabet[index]==radioValue"></radio>
							<text>{{alphabet[index]}} 、{{item}}</text>
						</view>
					</view>
				</radio-group>

				<!-- 判断 -->
				<radio-group v-if=" final[questionIndex-1].quesType==3" @change="radioChange">
					<view v-for="(item, index) in final[questionIndex-1].options" :key="index">
						<view class="options" @click="redioCheck(index) ">
							<radio :disabled="isDisabled" :value="alphabet[index]" :color="color" :checked="alphabet[index]==radioValue"></radio>
							<text>{{alphabet[index]}} 、{{item=='A'?"正确":"错误"}}</text>
						</view>
					</view>
				</radio-group>

				<!-- 多选 -->
				<checkbox-group v-if="final[questionIndex-1].quesType==2" @change="checkboxChange">
					<view v-for="(item, index) in final[questionIndex-1].options" :key="index">
						<view class="options" @click="checkboxCheck(index)">
							<checkbox :disabled="isDisabled" :value="alphabet[index]" :color="color" :checked="checkboxValue.some(item=>item==alphabet[index])  "></checkbox>
							<text>{{alphabet[index]}} 、{{item}}</text>
						</view>
					</view>
				</checkbox-group>


			</view>

			<view class="answer" v-if="answer" style="height: height: 57px;">
				<text v-if="right" style="color:blue">回答正确</text>
				<text v-else style="color:#FF0000 ;"> 回答错误</text>
				<view class="myAnswer">
					<text>参考答案: </text>
					<text style="color:#007AFF;font-weight: bold;" v-if="final[questionIndex-1]&&final[questionIndex-1].answer">{{final[questionIndex-1].answer}}</text>
					<text style="margin-left: 80rpx;">我的答案: </text>
					<text style="color: #007AFF;font-weight: bold;" v-if="myAnswer &&right">{{myAnswer}}</text>
					<text style="color: #FF0000;font-weight: bold;" v-if="myAnswer&&!right">{{myAnswer}}</text>
				</view>
			</view>
		</view>
		<!-- <view v-else  style="text-align: center;"class="toAnswer">
			<text>----暂无题----</text>
		</view> -->

		<view class="switch" v-if='final.length>0'>
			<button @click="toAgo()" :disabled="disabledAgo">上一题</button>
			<button @click="toGo()" :disabled="disabledTo">下一题</button>
		</view>

	</view>
</template>


<script>
	import {
		getAllProblem,
		getATypeProble,
		addPraticeRecord,
		getPraticeRecord,
		getPraticeRecordById,
		upatePraticeRecord
	} from '../../../api/pratice/pratice.js'
	import {
		getInfo
	} from '../../../api/my/my.js'
	export default {
		data() {
			return {

				kind: '',
				questionIndex: 1,
				question: [],
				// topicStyle:0, //0：单选，1多选
				// topicTitle:'遇到火灾，你应该迅速向哪个方向逃生？',
				alphabet: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'],
				options: [],
				radioValue: null, //标记是否被点击
				checkboxValue: [],
				answer: false, //显示答案
				first: [],
				final: [],
				data: '',
				myAnswer: '',
				color: '#4E74DC',
				index: null,
				right: false,
				safeType: null, //安全分类
				userId: null,
				finishRepoIds: null, //已做题目id
				returnRecord: [],
				isDisabled: false,
				judgeToRecordNum: 0, //点击上一题时的最大答题题号
				myAnswerBefore: [],
				isChecked: false,
				record: [],
				id: null,
				answerRecord: [],
				disabledTo: false,
				disabledAgo: false,
				isLoading:true
				// num:1,

			}
		},
		onLoad(option) {
			if(this.isLoading == true){
				uni.showLoading({
				    title: '加载中'
				});
			}
			this.data = JSON.parse(decodeURIComponent(option.data));
			this.getATypeProble(this.data.id)
			console.log(this.isLoading)
			
			//题库种类 
			this.kind = this.data.name
			//获取安全分类
			this.safeType = this.data.id
			//获取用户id
			this.getUser()
			
			
		},
		methods: {
			//通过id获取练习记录
			getRecord(id) {
				getPraticeRecordById(id).then(res => {
					this.answerRecord = res.data
					// console.log(this.answerRecord, 'this.answerRecord')
					console.log(this.questionIndex,'this.questionIndex222')
					
					if (this.answerRecord) {
						this.questionIndex = this.answerRecord.length + 1
						// this.questionIndex = this.answerRecord.length
						this.judgeToRecordNum = this.answerRecord.length
						
						this.record = this.answerRecord
					}else{
						if(this.questionIndex == 1){
							this.disabledAgo = true
						}
					}
					
					if(this.judgeToRecordNum == this.final.length){
						if(this.questionIndex == 2){
							this.disabledAgo = true
						}
						if(this.final.length>0){
							this.questionIndex = this.answerRecord.length
							this.myAnswer = this.record[this.judgeToRecordNum-1].finishQuesAnswer
							this.radioValue = this.myAnswer
							this.checkboxValue = this.myAnswer.split(',')
							this.judgeAnswer()
							this.answer = true
							this.isDisabled = true
							this.disabledTo = true
						}
						
					}
					console.log(777,this.record)
				})
			},

			//获取所有题目
			// async getAllPratice(index){
			// 	const res = await getAllProblem()
			// 	this.first = res.data;
			// 	this.filter(index)
			// },
			//获取相应分类下的所有题目
			async getATypeProble(index) {
				await getATypeProble(index).then(res =>{
					
					this.id = res.data.practiceRecordId
					this.first = res.data.repositoryDtos
					console.log(1111111,this.first)
					this.filter()
					this.getRecord(this.id)
					this.isLoading=false
					if(this.isLoading == false){
						 uni.hideLoading();
					}
				})
				
			},
			//获取用户登录信息
			async getUser() {
				const a = await getInfo();
				let result = await a;
				this.userId = result.data.user.id
			},
			//添加相应的练习记录
			addPraticeRecord() {
				const data = {
					safeTypeId: this.safeType,
					userId: this.userId,
					id: this.id, //练习记录的id
					finishRepoIds: this.finishRepoIds, //已做题目id
					finishQuesAnswer: this.myAnswer, //用户答案

				}
				//只有用户选择答案，才会添加记录
				addPraticeRecord(data).then(res => {})
				this.record.push(data)
				console.log(this.record, 'this.record')
			},
			//修改练习记录
			updateRecord() {
				const data = {
					id: this.id, //练习记录的id
					finishRepoIds: this.finishRepoIds, //已做题目id
					finishQuesAnswer: this.myAnswer, //用户答案
				}
				// if (data.finishQuesAnswer != null && data.finishQuesAnswer != '') {
					upatePraticeRecord(data).then(res => {})
				// }
			},
			//获取相应的练习记录
			getPraticeRecord() {
				let data = {
					safeTypeId: this.safeType,
					userId: this.userId,
				}
				getPraticeRecord(data).then(res => {
					this.returnRecord = res.data
					this.returnRecord.forEach(item => {
						if (item.finishRepoIds == this.finishRepoIds) {
							this.myAnswer = item.finishQuesAnswer
							this.radioValue = this.myAnswer
							this.checkboxValue = this.myAnswer.split(',')
							this.judgeAnswer()
						}
					})
				})
			},
			//对富文本中的图片显示限制大小
			replaceDetail(details){
				var texts='';//待拼接的内容
				while(details.indexOf('<img')!=-1){//寻找img 循环
					texts+=details.substring('0',details.indexOf('<img')+4);//截取到<img前面的内容
					details = details.substring(details.indexOf('<img')+4);//<img 后面的内容
					if(details.indexOf('style=')!=-1 && details.indexOf('style=')<details.indexOf('>')){
						texts+=details.substring(0,details.indexOf('style="')+7)+"max-width:100%;height:auto;margin:0 auto;";//从 <img 后面的内容 截取到style= 加上自己要加的内容
						details=details.substring(details.indexOf('style="')+7); //style后面的内容拼接
					}else{
						texts+=' style="max-width:100%;height:auto;margin:0 auto;" ';
					}
				}
				texts+=details;//最后拼接的内容
				return texts
			},
			//帅选出来该题库种类的题目、对答案分割做处理
			filter() {
				this.first.forEach(item => {
					item.quesContent = this.replaceDetail(item.quesContent)
					item.options = item.options.split('|')
					item.answer = item.answer.replace(/\|/g, ',')
					this.final.push(item)
					this.final.forEach(item => {
						if (item.quesType == 3) {
							item.options = ['A', 'B']
							if (item.answer == 'Y') {
								item.answer = 'A'
							} else if (item.answer == 'N') {
								item.answer = 'B'
							}
						}
					})

				})
			},

			//<radio-group> 中的选中项发生变化时触发 change 事件，event.detail = {value: 选中项radio的value}
			//返回选项的value值
			radioChange(value) {
				this.myAnswer = value.detail.value
			},


			checkboxChange: function(e) {},

			//单选自定义选择可取消
			redioCheck(index) {
				if (this.isDisabled == false) {
					this.index = index
					if (this.radioValue == this.alphabet[index]) {
						this.radioValue = null
						this.myAnswer = null //点击文字也可以动态获取选项值
					} else {
						this.radioValue = this.alphabet[index]
						this.myAnswer = this.alphabet[index]
					}
				}
			},
			//多选自定义
			checkboxCheck(index) {
				if (this.isDisabled == false) {
					this.index = index
					if (this.checkboxValue.some(item => item == this.alphabet[index])) {
						// console.log("如果返回true进入if")
						//如果选中就从数组中移除
						this.checkboxValue.forEach((item, num) => {
							if (item == this.alphabet[index]) {
								this.checkboxValue.splice(num, 1)
							}
						})
						this.myAnswerBefore.forEach((item, num) => {
							if (item == this.alphabet[index]) {
								this.myAnswerBefore.splice(num, 1)
							}
						})
					} else {
						this.checkboxValue.push(this.alphabet[index])
						this.myAnswerBefore.push(this.alphabet[index])

					}
					var myAnswerBefore2 = this.myAnswerBefore.sort()
					this.myAnswer = myAnswerBefore2.join(',')
				}

			},

			//判断答题是否正确
			judgeAnswer() {
				var rightAnswer = this.final[this.questionIndex - 1].answer
				if (this.myAnswer == rightAnswer) {
					this.color = '#4E74DC'
					this.right = true
				} else {
					this.right = false
					this.color = '#FF6C6C'
				}
			},
			//上一题
			toAgo() {
				this.disabledAgo = false
				this.disabledTo = false
				this.isDisabled = true
				if (this.questionIndex > 1) { //是否是第一个
					this.isDisabled = false
					this.isDisabled = true
					this.answer = true
					this.color = '#4E74DC'
					this.radioValue = null //选项初始化清空
					this.checkboxValue = []
					this.myAnswerBefore = []
					this.myAnswer = ''
					this.judgeAnswer()
					this.questionIndex = this.questionIndex - 1
					this.finishRepoIds = this.final[this.questionIndex - 1].id
					console.log(4444,this.record)
					this.record.forEach(item => {
						console.log(222,item.finishRepoIds)
						console.log(3333,this.finishRepoIds)
						if (item.finishRepoIds == this.finishRepoIds) {
							this.myAnswer = item.finishQuesAnswer
							console.log(111,item.finishQuesAnswer)
							this.radioValue = this.myAnswer
							this.checkboxValue = this.myAnswer.split(',')
							this.judgeAnswer()
							// this.myAnswer = ''
						}
					})
				} else {
					if (this.judgeToRecordNum > this.questionIndex) {
						this.isDisabled = true
					} else {
						this.isDisabled = false
					}
					this.disabledAgo = true
					this.isDisabled = true
					uni.showToast({
						icon: "none",
						title: "已经是第一题",
						mask: true
					})
				}
			},

			//下一题
			toGo() {
				console.log(this.questionIndex,'questionIndellllllllllllllllllll')
				this.disabledAgo = false
				this.answer = false
				this.isDisabled = false
				if (this.judgeToRecordNum > this.questionIndex) {
					if (this.questionIndex < this.final.length) {
						// this.judgeToRecordNum = this.judgeToRecordNum+1
						this.questionIndex = this.questionIndex + 1
						this.radioValue = null //选项初始化清空
						this.color = '#4E74DC'
						this.checkboxValue = []
						this.myAnswerBefore = []
						this.myAnswer = ''
						this.isDisabled = true
						this.answer = true
						// this.judgeAnswer()
						this.finishRepoIds = this.final[this.questionIndex - 1].id //获取当前题目id
						this.record.forEach(item => {
							if (item.finishRepoIds == this.finishRepoIds) {
								this.myAnswer = item.finishQuesAnswer
								this.radioValue = this.myAnswer
								this.checkboxValue = this.myAnswer.split(',')
								this.judgeAnswer()
							}
						})
					}
				} else if (this.judgeToRecordNum == this.questionIndex ) {
					this.questionIndex = this.questionIndex + 1
					this.isDisabled = false
					this.answer = false
					this.radioValue = null //选项初始化清空
					this.color = '#4E74DC'
					this.checkboxValue = []
					this.myAnswerBefore = []
					this.myAnswer = ''
				}
				 else {
					if (this.questionIndex <= this.final.length) {
						this.finishRepoIds = this.final[this.questionIndex - 1].id
						this.addPraticeRecord()
						this.isDisabled = true
						this.judgeAnswer()
						this.answer = true
						this.judgeToRecordNum = this.judgeToRecordNum + 1
						setTimeout(() => {
							if (this.questionIndex < this.final.length) {
								this.color = '#4E74DC'
								this.radioValue = null //选项初始化清空
								this.checkboxValue = []
								this.myAnswerBefore = []
								this.myAnswer = ''
								this.questionIndex = this.questionIndex + 1
								this.answer = false
								this.isDisabled = false
								this.finishRepoIds = this.final[this.questionIndex - 1].id //获取当前题目id
							} else {
								this.isDisabled = true
								this.disabledTo = true
								uni.showToast({
									icon: "none",
									title: "已经是最后一题",
									mask: true
								})
							}
						}, 1000)
						// }
					} else {
						console.log(this.questionIndex,'questionIndex1222')
						this.isDisabled = true
						this.disabledTo = true
						uni.showToast({
							icon: "none",
							title: "已经是最后一题",
							mask: true
						})
					}

				}

			}
		}
	}
</script>

<style scoped lang="scss">
	.pratice{
		min-height: 620px;
	}
	.top {
		background-color: #FF4848;
		margin-top: -2px;
		height: 40rpx;

		text {
			display: inline-block;
			margin: 15px 30px 0px 20px;
		}
	}

	.toAnswer {
		min-height: 500px;
		/* border: 1px solid #000000; */
		border-radius: 5px;
		margin: 10px 12px;
		box-shadow: 0px 0px 5px 2.5px #EBEDEE;
		font-size: 15px;

		.topic {
			padding: 10px 20px;
		}

		.topinOptions {
			padding: 10px 20px;

			.options {
				display: block;
				padding-bottom: 20rpx;
			}

		}
		.answer {
			
			width: 90%;
			border-top: 1px solid #E6E4E4;
			margin: 100rpx auto;
			padding-bottom: 20rpx;
			padding-top: 20rpx;
		
			.myAnswer {
				background-color: #F0F0F0;
				font-size: 28rpx;
				height: 106rpx;
				line-height: 106rpx;
				border-radius: 6.3;
				padding-top: 5px;
				margin-top: 20rpx;
		
				text {
					display: inline-block;
					padding-left: 20rpx;
				}
			}
		
			// background-color: #E6E4E4;
			// line-height: 28.5px;
		
		}
	}

	.switch {
		width: 100%;
		text-align: center;
		margin-top: 20px;
		// position: fixed;

		button {
			width: 100px;
			// display: inline-flex;
			justify-content: space-around;
			display: inline-block;
			margin: 0 20px;
			background-color: #FDBB53;
			font-size: 15px
		}
	}

	.richText /deep/ div{
		display: inline;
	}

	.blue {
		color: #4E74DC;
	}
</style>
