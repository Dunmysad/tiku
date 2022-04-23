import request from '@/utils/request'

export function getCases(params) {
  return request({
    url: 'api/eSafeCase',
    method: 'get',
    params
  })
}

export function getCasesSuperior(pid) {
  return request({
    url: 'api/eSafeCase?pid=' + pid,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/eSafeCase',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/eSafeCase',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/eSafeCase',
    method: 'put',
    data
  })
}

export function getSafeType() {
  return request({
    url: '/api/eSafeType/allName',
    method: 'get',
  })
}

export default { add, edit, del, getCases, getCasesSuperior, getSafeType }
