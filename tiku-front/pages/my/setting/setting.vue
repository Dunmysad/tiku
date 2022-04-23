<template>
	<view>
		<view style="height: 44px;background-color: #FF4848;"></view>
		<view class="top">
			<image class="back" src='../../../static/my/youjiantou.svg' mode="" @click="back()"></image>
			<text>设置</text>
		</view>
		<view class="bottom">
			<view class="list" style="margin-right: -5px;" @click="chooseAvatar">
				<text >修改头像</text>
				<image class="head" :src='avatar' mode="aspectFill" style="width: 32px;height: 32px;padding-top:0px"></image>
			</view>
			<!-- <view class="u-avatar-wrap">
				<image class="u-avatar-demo" :src="avatar" mode="aspectFill"></image>
			</view>
			<u-button @click="chooseAvatar">进入裁剪页</u-button> -->
			<view class="list" @click="toSkip(1)">
				<text>修改密码</text>
				<image src='../../../static/my/big.png' mode=""></image>
			</view>

			<view class="list" @click="toSkip(2)" v-if="isShow">
				<text>隐私政策与用户服务协议</text>
				<image src='../../../static/my/big.png' mode=""></image>
			</view>
		</view>

	</view>
</template>

<script>
	import {
		updateAvatar,
		getInfo
	} from '../../../api/my/my.js'
	import baseUrl from '../../../utils/env.js';
	export default {
		data() {
			return {
				avatar: '../../../static/my/touxiang.png', //头像
				public: '1.0', //程序版本
				skipUrl: [{
					key: 1,
					url: 'password/password',
				}],
				isShow: false,
				// avatar: 'https://cdn.uviewui.com/uview/common/logo.png',
			}
		},
		onLoad() {
			this.getUserInfo()
		},
		created() {
			// 监听从裁剪页发布的事件，获得裁剪结果
			uni.$on('uAvatarCropper', path => {
				let token = uni.getStorageSync("token");
				this.avatar = path;
				// 可以在此上传到服务端
				uni.uploadFile({
					url: baseUrl + '/users/updateAvatar',
					filePath: path,
					name: 'avatar',
					header: {
						Authorization: token
					},
					complete: (res) => {
						console.log(res);
					}
				});
			})
		},
		methods: {
			chooseAvatar() {
				// 此为uView的跳转方法，详见"文档-JS"部分，也可以用uni的uni.navigateTo
				uni.navigateTo({
					// 关于此路径，请见下方"注意事项"
					url: '../../u-avatar-cropper/u-avatar-cropper?rectWidth='+200+'&&destWidth='+200+'&&fileType='+'jpg',
				})
			},
			getUserInfo() {
				getInfo().then(res => {
					// console.log(res.data, 'res.data')
					if (res.data.user.avatarPath) {
						this.avatar = baseUrl.replace('api','api/file/download/') + res.data.user.avatarPath
					}
				})
			},
			back() {
				uni.reLaunch({
					url: '../my'
				})
			},
			//点击确定
			dialogConfirm() {
				this.$refs.popup.close();
			},
			//点击取消
			dialogClose() {
				this.$refs.popup.close();
			},
			uploadTouxiang() {
				let token = uni.getStorageSync("token");
				let that = this;
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: function(res) {
						console.log(JSON.stringify(res.tempFilePaths));
						console.log(res, "res.data")
						const tempFiles = res.tempFiles;
						const tempFilePaths = res.tempFilePaths
						uni.uploadFile({
							url: baseUrl + '/users/updateAvatar', //post请求的地址
							// url:baseUrl+'/EToolFile',
							filePath: tempFilePaths[0],
							name: 'avatar',
							header: {
								Authorization: token
							},
							success: (uploadFileRes) => {
								//这里要注意，uploadFileRes.data是个String类型，要转对象的话需要JSON.parse一下
								const back = JSON.parse(uploadFileRes.data);
								that.getUserInfo()
								// this.head_img = getApp().globalData.testUrl.replace('api','resource/')+back.data.avatar

							}
						})

					}
				});

			},
			toSkip(index) {
				if (index == 0) {
					this.uploadTouxiang()
				} else if (index == 1) {
					uni.navigateTo({
						url: './password/password'
					})
				} else if (index == 2) {
					uni.navigateTo({
						url: '../aboutOur/aboutOur'
					})
				}

			}
		}
	}
</script>

<style scoped lang="scss">
	.top {
		height: 46px;
		line-height: 46px;
		background-color: #FF4848;
		border-top: 1px solid #FF4848;

		// font-size: #fff;
		text {
			height: 20px;
			font-size: 32rpx;
			color: #FAF6F6;
		}
	}

	.back {
		float: left;
		display: inline-block;
		margin:8px 15px 0 15px;
		height: 30px;
		width: 36px;
		// top: 50%;
	}

	.bottom {
		.list {
			border-bottom: 1px solid #EDEAEA;

			text {
				display: inline-block;
				font-size: 34rpx;
				color: #404040;
				margin: 20px 30px;
			}

			image {
				margin: 15px 30px;
				padding-top: 9px;
				width: 20px;
				height: 22px;
				float: right;
				border-radius: 50%;
			}
		}
	}

	.u-avatar-wrap {
		margin-top: 80rpx;
		overflow: hidden;
		margin-bottom: 80rpx;
		text-align: center;
	}

	.u-avatar-demo {
		width: 150rpx;
		height: 150rpx;
		border-radius: 100rpx;
	}
</style>
