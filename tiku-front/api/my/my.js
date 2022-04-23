import {get,deletes,post,put} from "../../utils/request.js"
import baseUrl from '../../utils/env.js';
//修改用户：个人中心
export function editUser(data) {
	return put('/users/center',data)
}
//获取用户信息
export function getInfo() {
  return get('/auth/info')
}

//修改密码
export function updatePass(pamas){
	return post('/users/updatePass',pamas)
}

//修改头像
export function updateAvatar(avatar){
	return post('/users/upstaeAvatar',avatar)
}

//退出登录
export function logout(){
	return deletes('/auth/logout')
}
