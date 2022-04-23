import request from '@/utils/request'

export function getAllExercise() {
  const params = {
    endTime: '',
    id: '',
    isCheck: '',
    SafeArrayTypeld: '',
    quesContent: '',
    startTime: '',
    quesType: ''
  }
  return request({
    url: '/api/eRepository/all',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: '/api/eRepository',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: '/api/eRepository',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: '/api/eRepository',
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


export default { add, edit, del, getSafeType }
