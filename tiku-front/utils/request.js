import Axios from 'axios';
import Config from '../setting.js';
import baseUrl from './env.js';

//是否登录
Axios.isLogin = false
//设置超时
// Axios.defaults.timeout = 12000;
//设置请求基地址
Axios.defaults.baseUrl = baseUrl;
// 添加请求拦截器
Axios.interceptors.request.use((request) => {
	// 给所有请求添加自定义header，带上token信息让服务器验证用户登陆、
	// let token = getApp().globalData.token;
	// console.log(token+"[token]");
	// console.log(token+"[token]");
	// request.headers['Authorization'] = token;
	// request.headers['content-type'] = 'application/json';
	// let token = getApp().globalData.token;
	let token =  uni.getStorageSync("token");
	request.headers['Authorization'] = token;
	request.headers['content-type'] = 'application/json';
	
	return request;
})



// 添加响应拦截器
Axios.interceptors.response.use(
	(response) => {
		// console.log(response,"response")
		const code = response.status;
		console.log(code,'code')
		if (code < 200 || code > 300) {
			uni.hideLoading()
			uni.showToast({
				icon: "none",
				title: response.message,
				mask: true
			})
			return Promise.reject('error');
		} else {
			// 如果为undefined 说明为下载接口，无code
			if (response.data.code === undefined) {
				return response.data;
			} else if (response.data.code !== 0) {
				uni.hideLoading()
				uni.showToast({
					icon: "none",
					title: response.data.message,
					mask: true
				})
				return Promise.reject(response.data);
			}
			return response.data;
		}
	},
	(error) => {
		let code = 0;
		console.log(error.response,"response.erro")
		try {
			code = error.response.data.status;
		} catch (e) {
			if (error.toString().indexOf('Error: timeout') !== -1) {
				uni.showToast({
					icon: "none",
					title: '网络请求超时',
					mask: true
				});
				return Promise.reject(error);
			}
		}
		if (code) {
			if (code === 401) {
				uni.showModal({
				    title: '您还未登录，请先登录',
				    // content: '这是一个模态弹窗',
				    success: function (res) {
				        if (res.confirm) {
							uni.setStorageSync('userInfo', null);
							getApp().globalData.userInfo = null
							if (getApp().globalData.isSocketConnect) {
								uni.closeSocket();
								getApp().globalData.isSocketConnect = false;
							}
				            setTimeout(function () {
				            	uni.navigateTo({
				            		url: '/pages/login/login'
				            	});
				            }, 0);
				        } else if (res.cancel) {
							uni.navigateBack({
								delta: 1
							});
				           
				        }
				    }
				});
				// uni.setStorageSync('userInfo', null);
				// getApp().globalData.userInfo = null
				// if (getApp().globalData.isSocketConnect) {
				// 	uni.closeSocket();
				// 	getApp().globalData.isSocketConnect = false;
				// }
				// setTimeout(function () {
				// 	uni.reLaunch({
				// 		url: '/pages/login/login'
				// 	});
				// }, 2000);
			} else if (code === 403) {
				uni.showToast({
					icon: "none",
					title: '您的登录已过期，请再次登录!',
					mask: true,
					duration: 2000
				});
				uni.setStorageSync('userInfo', null);
				getApp().globalData.userInfo = null
				if (getApp().globalData.isSocketConnect) {
					uni.closeSocket();
					getApp().globalData.isSocketConnect = false;
				}
				setTimeout(function () {
					// uni.reLaunch({
					// 	url: '/pages/login/login'
					// });
				}, 2000)

			} else {
				const errorMsg = error.response.data.message;
				if (errorMsg !== undefined) {
					uni.hideLoading()
					uni.showToast({
						icon: "none",
						title: errorMsg,
						mask: true
					});
				}
			}
		} else {
			uni.hideLoading()
			uni.showToast({
				icon: "none",
				title: '服务器异常,请重试!',
				mask: true
			});
		}
		return Promise.reject(error);
	}
)
// 真机获取  
Axios.defaults.adapter = function (config) {
	return new Promise((resolve, reject) => {
		var settle = require('axios/lib/core/settle');
		var buildURL = require('axios/lib/helpers/buildURL');
		uni.request({
			method: config.method.toUpperCase(),
			url: baseUrl + buildURL(config.url, config.params, config.paramsSerializer),
			header: config.headers,
			data: config.data,
			dataType: config.dataType,
			responseType: config.responseType,
			sslVerify: config.sslVerify,
			complete: function complete (response) {
				response = {
					data: response.data,
					status: response.statusCode,
					errMsg: response.errMsg,
					header: response.header,
					config: config
				};

				settle(resolve, reject, response);
			}
		})
	})
}

/**
 * get方法封装
 */
export function get (url, data) {
	return Axios.get(url, {
		params: data
	})
}

/**
 * delect方法封装
 */
export function deletes (url, data) {
	return Axios.delete(url, {
		params: data
	})
}

/**
 * post方法封装
 */
export function post (url, param) {
	return Axios.post(url, param)
}

/**
 * put方法封装
 */
export function put (url, param) {
	return Axios.put(url, param)
}

export default Axios ;
