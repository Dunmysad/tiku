import {get,deletes,post,put} from "../../utils/request.js"

//分页获取今日推荐
export function getStudyData (data) {
	return get('/eStudyData/front/daily',data)
}
//浏览量
export function getLookNum(data){
	return post('/eStudyData/front/read',data)
}

