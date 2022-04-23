import {get,deletes,post,put} from "../../utils/request.js"

//分页获取案例
export function getAllCase (data) {
	return get('/eSafeCase/front-all',data)
}
//根据关键字查询案例
export function queryCase(data){
	return get('/eSafeCase',data)
}

//根据id查询案例
export function queryCaseId(id){
	return get('/eSafeCase/front',id)
}

//踩了踩
export function caseDislikeNum(data){
	return post('/eSafeCase/front/dislike',data)
}

//赞了赞
export function caseLikeNume(data){
	return post('/eSafeCase/front/like',data)
}

//看了看
export function addCaseView(data){
	return post('/eSafeCase/front/read',data)
}
