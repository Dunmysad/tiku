import request from '@/utils/request'

export function getAllStudents() {
  const params = {
    // page: 0,
    // size: 9999,
    // enabled: true
  }
  return request({
    url: '/api/eStudent',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: '/api/eStudent',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: '/api/eStudent',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: '/api/eStudent',
    method: 'put',
    data
  })
}
export function getDepts() {
  return request({
    url: '/api/dept/all',
    method: 'get',
  })
}
// export function getDepts() {
//   return request({
//     url: '/api/dept',
//     method: 'get',
//   })
// }

export default { add, edit, del,getDepts}
