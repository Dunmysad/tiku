<template>
	<view v-if="alldetail&&record_detail">
		<!-- <Pratice></Pratice> -->
		<view class="title">
			<image class="back" src="../../../../static/my/back.png" mode="" @click="back()"></image>
			<text>答题报告</text>
		</view>
		<view class="test11111" v-if="alldetail!=null&&record_detail!=null">
			<pratice :alldetail ="alldetail" :filterFinal="record_detail" :questionIndex="index" :record_id="examRecordId" ></pratice>
		</view>
	</view>
</template>

<script>
	import pratice from '../../../../components/pratice.vue'
	import {getRecordQuestionDetail} from '@/api/record/record.js'
	export default {
		name:'record_detail',
		components:{pratice},
		data() {
			return {
				record_detail:[],
				index:null,
				aData:{},
				examDetailId:null,
				alldata:{},
				alldetail:null,
				examRecordId:null
			}
		},
		methods: {
			back(){
				uni.navigateBack({
					delta:1
				})
				// uni.navigateTo({
				// 	url:'../record/record'
				// })
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
			encodeSearchKey(key) {
			    const encodeArr = [{
			      code: '%',
			      encode: '%25'
			    }, {
			      code: '?',
			      encode: '%3F'
			    }, {
			      code: '#',
			      encode: '%23'
			    }, {
			      code: '&',
			      encode: '%26'
			    }, {
			      code: '=',
			      encode: '%3D'
			    }];
			    return key.replace(/[%?#&=]/g, ($, index, str) => {
			      for (const k of encodeArr) {
			        if (k.code === $) {
			          return k.encode;
			        }
			      }
			    });
			  },
			
		},
		onLoad(option){
			this.aData  =  JSON.parse(decodeURIComponent(decodeURI(option.data)))
			this.alldata  =  JSON.parse(decodeURIComponent(decodeURI(option.alldata)))
			this.alldetail = this.alldata 
			this.record_detail = this.aData 
			this.record_detail.quesContent = this.replaceDetail(this.record_detail.quesContent)
			this.examRecordId = option.recordId
			this.index = option.key
			console.log("alldetail",this.alldetail)
			console.log("record_detail",this.record_detail)
		}
	}
</script>

<style scoped lang="scss">
.title{
		margin: 40rpx 0;
		padding-top:40rpx ;
		font-size:32rpx;
		text{
			line-height: 88rpxrpx;
		}
		.back{
			display: inline-block;
			// padding-top: 30rpx;
			height: 18px;
			width: 18px;
			float: left;
			margin: 0rpx 40rpx;
		}
	}
</style>
