import request from '@/utils/request'

// 查询备份日志列表
export function listBackupLog(query) {
  return request({
    url: '/system/backupLog/list',
    method: 'get',
    params: query
  })
}

// 查询备份日志详细
export function getBackupLog(backupTime) {
  return request({
    url: '/system/backupLog/' + backupTime,
    method: 'get'
  })
}

// 新增备份日志
export function addBackupLog(data) {
  return request({
    url: '/system/backupLog',
    method: 'post',
    data: data
  })
}

// 修改备份日志
export function updateBackupLog(data) {
  return request({
    url: '/system/backupLog',
    method: 'put',
    data: data
  })
}

// 删除备份日志
export function delBackupLog(backupTime) {
  return request({
    url: '/system/backupLog/' + backupTime,
    method: 'delete'
  })
}

// 备份表
export function getTableData(query) {
  return request({
    url: '/system/backupLog/getTableData',
    method: 'get',
    params: query
  })
}
