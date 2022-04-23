import Vue from 'vue'
import App from './App'
import uView from "uview-ui";
Vue.use(uView);
// 引入
//
// import JSEncrypt from 'node_modules/jsencrypt/bin/jsencrypt.js'
// Vue.prototype.$jsencrypt = JSEncrypt

// 封装的uni-request请求，因为调用接口都需要用到，所以挂在全局(即挂载到vue原型上)
import myRequest from './utils/request.js'
Vue.prototype.$myRequest = myRequest

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
