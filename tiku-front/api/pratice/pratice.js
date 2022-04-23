// import Axios from '../../utils/request.js'
import {get,deletes,post,put} from "../../utils/request.js"


//最高分考试记录
export function getHignScore(){
	return get('/eExamRecord/maxScore')
}
//获取所有题目
export function getAllProblem(data){
	return get('/eRepository/all',data)
}
//分页获取所有安全分类
export function getAllSafeType(data){
	return get('/eSafeType',data)
}

//按照分类id获取相应的题库
export function getATypeProble(data){
	return get('/eRepository/getBySafeTypeId?id='+data)
}
//前台获取所有安全分类
export function getSafeType(){
	return get('/eSafeType/front-all')
}


//获取所有安全分类名称
export function getSafeTypeName(){
	return get('/eSafeType/allName')
}


//条件查询练习记录
export function getPraticeRecord(data){
	return get('/ePracticeRecord/all?safeTypeId='+data.safeTypeId+'&&userId='+data.userId)
}
//添加练习记录
export function addPraticeRecord(data){
	return post('/ePracticeRecord',data)
}
//修改练习记录
export function upatePraticeRecord(data){
	return put('/ePracticeRecord',data)
}

//根据练习记录id查询练习记录
export function getPraticeRecordById(id){
	return get('/ePracticeRecord/getEPracticeRecordById?id='+id)
}