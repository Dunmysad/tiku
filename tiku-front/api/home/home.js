import {
	get,
	post,
	put,
	deletes
} from '../../utils/request.js'

export function slide(){
	return get('/eSlideshow/all')
}

export function frontAll(){
	return get('/eSafeType/front-all')
}

// 获取考试基本信息
export function examInfo(params){
	return get('/examProcess/examInfo',params)
}

// 获取考卷
export function getExam(params){
	return get('/examProcess/exam',params)
}

// 确认开始考试
export function beginExam(params){
	return get('/examProcess/exam/begin/'+params)
}

// 提交答案
export function answer(params){
	return post('/examProcess/exam/answer',params)
}

// 交卷
export function status(params){
	return get('/examProcess/exam/status/'+params)
}

// 获取某一题目的答案
export function statusQues(params){
	return get('/examProcess/exam/status/ques',params)
}

// 分页查询某个安全类别的学习资料
export function studyData(params){
	return get('/eStudyData/front-all',params)
}

// 根据id查询学习资料详情
export function studyFront(params){
	return get('/eStudyData/front',params)
}

// 踩了踩
export function dislike(params){
	return post('/eStudyData/front/dislike',params)
}

// 赞了赞
export function like(params){
	return post('/eStudyData/front/like',params)
}

// 看了看
export function read(params){
	return post('/eStudyData/front/read',params)
}

export default {
	slide,
	frontAll,
	examInfo,
	getExam,
	beginExam,
	answer,
	status,
	statusQues,
	studyData,
	studyFront,
	dislike,
	like,
	read
}