import request from '@/utils/request'

export function getAllSecurity() {
  const params = {
    current: 1,
    size: 10,
    name: ''
  }
  return request({
    url: '/api/eSafeType',
    method: 'get'
    // params
  })
}

export function add(data) {
  return request({
    url: '/api/eSafeType',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: '/api/eSafeType',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: '/api/eSafeType',
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

export function updateType(data) {
  return request({
    url: '/api/eSafeType/update/state',
    method: 'put',
    data
  })
}

export default { add, edit, del, getSafeType,updateType}
