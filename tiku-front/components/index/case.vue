<template>
  <view class="case">
    <view scroll-y class="sace_scroll">
      <view class="a_case" v-for="(item,index) in final" :key="index" @click="lookcase(item,index)" >
        <view class="">
          <!-- <img  referrerPolicy="no-referrer"  :src="item.picPath" class="case_img" onerror="οnerrοr=null;src='../../static/exam/b1.png'" ></img> -->
			<img  referrerPolicy="no-referrer"  :src="item.picPath" class="case_img" @error="imageError($event, index)" ></img>	
		</view>
        <view class="title">
          <text class="safeType">{{item.name}}</text>
          <text class="case_title">{{item.title}}</text>    
          <text class="look" >{{item.pageView==null?0:item.pageView}}观看</text>
        </view>
      </view>
    </view>  
  </view>
</template>

<script>
  import {getSafeTypeName} from '@/api/pratice/pratice.js'
  import { addCaseView ,getAllCase} from '@/api/case/case.js'
  import {getLookNum,getStudyData} from '@/api/study/study.js'
  import baseUrl from '@/utils/env.js'
  import Home from '../../api/home/home.js'

  export default{
    // props:['final','title' ,'type'],
	props:['title','onReach' ],
    data(){
      return{
        // searchValue:'',
		study:[],
        cases:[],
        page:1,
        data:[],
        value:this.title,
        view:[],
        // newFinal:this.final,
		final:null,
		allData:null,
		studyData:null,
		type:null,
		data1:{
			current:1,
			size:10
		},
		data2:{
			current:1,
			size:10
		},
		onReachBottom:this.onReach
      }
    },
    created(){
		this.getType()
		this.getStudyData(this.data1)
		this.getAllCases(this.data2)
		this.final = this.study
		if(this.title =="今日推荐"){
			this.final = this.study
		}
		if(this.title =='经典案例'){
			this.final = this.cases
		}
    },
    watch:{
		//捕获title的切换变化
      title(newVal,oldVal){
        this.value = newVal
		if(this.value =="今日推荐"){
			this.final = this.study
		}
		if(this.value =='经典案例'){
			this.final = this.cases
		}
      },
	  onReach(newVal,oldVal){
		  if(newVal != oldVal){
			  this.lastUpdate()
		  }
	  }
    },
	
		methods:{
			//上拉加载
			lastUpdate(){
				if(this.value=='今日推荐'){
						if(this.study.length>=this.studyData){
							
						}else{
							this.data1.current = this.data1.current+1
							this.getStudyData(this.data1)
							this.final = this.study
						}
						
					}
						
					if(this.value=='经典案例'){
						if(this.cases.length>=this.allData){
							
						}else{
							this.data2.current = this.data2.current+1
							this.getAllCases(this.data2)
							this.final  = this.cases
						}
					}
			},
			
			 imageError(e, index) {
			     this.final[index].picPath = '../../static/fonts/zanwu.png';
			 },
			 //全部安全分类
			 getType(){
			 	getSafeTypeName().then(res=>{
			 		this.type = res.data
			 	})
			 },
			 //对数据进行处理，修改正确图片路径，添加相应分类名称
			 filter(data){
			 	if(data !=null||data.length>0){
			 		data.forEach(item=>{
			 			if(item.coverPicLink!=undefined){
			 				item.picPath = getApp().globalData.testUrl.replace('api','api/file/download/')+item.coverPicLink
			 			}else{
			 				item.picPath = getApp().globalData.testUrl.replace('api','api/file/download/')+item.picPath
			 			}
			 			this.type.forEach(item1=>{
			 				if(item.safeTypeId&&item.safeTypeId == item1.id ){
			 					item.name = item1.name
			 				}
			 				if(item.safeType&&item.safeType == item1.id ){
			 					item.name = item1.name
			 				}else if(item.safeType==0){
			 					item.name = '其他安全'
			 				}
			 			})
			 		})
			 	}
			 	
			 },
			 //获取今日推荐内容
			 getStudyData(data1){
			 	getStudyData(data1).then(res=>{
					// console.log(res.data,'获取今日推荐内容')
			 		this.studyData = res.data.total
			 		this.filter(res.data.records)
			 		this.study = [...this.study, ...res.data.records]
			 		if(this.value = '今日推荐'){
			 			this.final = this.study
			 		}
			 	})
			 },
			 //获取经典推荐内容
			 getAllCases(data2){
			 	getAllCase(data2).then(res=>{
			 		this.allData = res.data.total
			 		this.filter(res.data.records)
			 		this.cases = [...this.cases, ...res.data.records]
			 	})			
			 	
			 },
			//传递参数
			lookcase(item,index){
				if(item.pageView == null){
					item.pageView = 0
				}
				var data={
					id: item.id,
					count:item.pageView+1
				}
				if(this.value =='经典案例'){
					const that = this
					this.addViewCase(data,index)
					var itemobj = encodeURIComponent(JSON.stringify(item))
					uni.navigateTo({
							url:'../../pages/case/caseDetail/caseDetail?key='+item.id,
							success() {
								var data = {
									current:1,
									size:10
								}
								getAllCase(data).then(res=>{
									if(res.data){
										that.allData = res.data.total
										that.filter(res.data.records)
										that.cases = res.data.records
									}
									
								})
							}
					})
				}else {
					const that = this
					uni.navigateTo({
						url: '../study-info/study-info?id='+item.id+'&fromLunbo='+true,
						success() {
							var data = {
								current:1,
								size:10
							}
							getStudyData(data).then(res=>{
								if(res.data){
									that.studyData = res.data.total
									that.filter(res.data.records)
									that.study = res.data.records
								}
								
							})
						}
					})
				}
			},

      //经典案例浏览量+
      addViewCase(data,index){
        addCaseView(data).then(res=>{
          console.log(res,"addCaseView")
        })
      },
	  //今日推荐浏览量+
      addViewStudy(data,index){
        getLookNum(data).then(res=>{
        })
      }
    }
  }
  
</script>

<style scoped lang="scss">
  .case{
    .sace_scroll{
      height: 100%;
      width: 100%;
      margin: 0 auto;
      .a_case{
        display: flex;
        // box-shadow:0 1px 2px 2px #EBEBEB;
        margin: 5px auto;
        margin-bottom: 30rpx ;
        // width:345px;
        width: 100%;
        .case_img{
          border-top-left-radius: 4%;
          border-top-right-radius: 4%;
          border-radius: 4.5px;
          width: 250rpx;
          height: 160rpx;
          margin: 0 auto;
          margin-bottom: 4rpx;
        }
        
      }
    }
  }
  .title{
    position: relative;
    margin: 0px 20rpx;
    // height: 27px;
    // padding-bottom: 5px;
    text{
      // font-size: 28rpx;
      // margin:auto 15px;
      letter-spacing:0.5px
    }
    image{
      float: right;
      margin-right: 15px;
      width: 25px;
      height: 25px;
    }
  }
  .safeType{
    border-radius: 1.5px;
    background-color:#083ECF ;
    color: #fff;
    font-size: 22rpx;
    padding: 2rpx 12rpx;
  }
  .case_title{
    margin: 0 10rpx;
    color: #333;
    font-size: 32rpx;
    // overflow:hidden;
    // white-space:nowrap; //设置文本过长时不换行
    // text-overflow:ellipsis;
    // display:-webkit-box;
    // // display: inline-block;
    // // overflow:hidden;
    // text-overflow:ellipsis;
    // -webkit-box-orient:vertical;
    // -webkit-line-clamp:2;
  }
  .look{
    position: absolute;
    bottom: 0;
    // margin-top: 42px;
    display:block;
    color: #807e7e;
    font-size: 24rpx;
  }
</style>