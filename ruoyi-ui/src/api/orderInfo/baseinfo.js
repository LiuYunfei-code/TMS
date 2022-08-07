import request from '@/utils/request'

// 查询报表信息列表
export function listBaseinfo(query) {
  return request({
    url: '/orderInfo/baseinfo/list',
    method: 'get',
    params: query
  })
}

// 查询报表信息详细
export function getBaseinfo(id) {
  return request({
    url: '/orderInfo/baseinfo/' + id,
    method: 'get'
  })
}

// 新增报表信息
export function addBaseinfo(data) {
  return request({
    url: '/orderInfo/baseinfo',
    method: 'post',
    data: data
  })
}

// 修改报表信息
export function updateBaseinfo(data) {
  return request({
    url: '/orderInfo/baseinfo',
    method: 'put',
    data: data
  })
}

// 删除报表信息
export function delBaseinfo(id) {
  return request({
    url: '/orderInfo/baseinfo/' + id,
    method: 'delete'
  })
}
