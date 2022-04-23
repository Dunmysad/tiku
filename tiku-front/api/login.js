import {get,deletes,post,put} from "../utils/request.js"

//登录
export function login (params) {
	return post('/auth/reception/login', params)
}

//验证码的获取
export function getCode(){
	return get('/auth/code')
}

//获取所有部门
export function getAllDept(){
	return get('/dept/all')
}




