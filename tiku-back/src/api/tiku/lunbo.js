import request from '@/utils/request'

export function getAllLunbo() {
  const params = {
    current: '',
    orders: '',
    size: ''
  }
  return request({
    url: '/api/eSlideshow/all',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: '/api/eSlideshow',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: '/api/eSlideshow',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: '/api/eSlideshow',
    method: 'put',
    data
  })
}

export default { add, edit, del }