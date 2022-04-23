import {get,deletes,post,put} from "../../utils/request.js"

//获取所有考试记录
// export function getAllRecord(id){
// 	return get('/eExamRecord/'+id)
// }
//分页获取考试记录
export function getARecord(data){
	return get('/eExamRecord/allExamRecord',data)
}
//获取某个考试记录详情
export function getRecordDetail(data){
	return get('/eExamRecord/detail?examInfoId='+data.examInfoId+'&&examRecordId='+data.examRecordId)
}

//根据examInfoId和examRecordId查询考试记录
export function getRocordById(data){
	return get('/eExamRecord/bestExamRecord',data)
}

//获取某个考生记录的某一场考试某一道题的答题详情
export function getRecordQuestionDetail(data){
	return get('/eExamRecord/detail/question',data)
	// return get('/eExamRecord/detail/question?examDetailId='+data.examDetailId+'&&examRecordId='+data.examRecordId+'&&sort='+data.sort)
}

