<template>
	<view>
		<view class="toIndexIcon" @click="toIndex(url)">
			<image class="icon-fanhui" src="../../static/fonts/back.png"></image>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return {
				
			}
		},
		props:{
			url: {
				type:String
			}
		},
		methods: {
			toIndex(url){
				uni.navigateTo({
				    url
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.toIndexIcon{
		width: 100rpx;
		height: 60rpx;
		text-align: center;
		line-height: 60rpx;
		.icon-fanhui{
			width: 40rpx;
			height: 40rpx;
			position: relative;
			top: 8rpx;
		}
	}

</style>
