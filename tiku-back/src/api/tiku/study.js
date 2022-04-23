import request from '@/utils/request'

export function getStudys(params) {
  return request({
    url: 'api/eStudyData',
    method: 'get',
    params
  })
}

export function getStudysSuperior(pid) {
  return request({
    url: '/api/eStudyData?pid=' + pid,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/eStudyData',
    method: 'post',
    data
  })
}

export function del(ids) {
  console.log("ids:", ids)
  return request({
    url: 'api/eStudyData',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/eStudyData',
    method: 'put',
    data
  })
}

export function changeIntro(data) {
  return request({
    url: 'api/eStudyData/changeIntroduce',
    method: 'put',
    data
  })
}

export default { add, edit, del, getStudys, getStudysSuperior, changeIntro }
