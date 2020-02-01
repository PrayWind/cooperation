import request from '@/utils/request'

export function getProjectList(search) {
  const data = { search }
  return request({
    url: '/reports/getProjectList',
    method: 'post',
    data
  })
}

// 获取指标下的所有模板
export function allIndxTemplet(indxTreeId) {
  const data = {
    indxTreeId: indxTreeId
  }
  return request({
    url: '/reports/allIndxTemplet',
    method: 'post',
    data
  })
}

// 创建报告
export function saveReport(formData, templetList) {
  const data = {
    formData: formData,
    templetList: templetList
  }
  return request({
    url: '/reports/saveReport',
    method: 'post',
    data
  })
}

// 获取用户列表
export function userList(projectId) {
  const data = {
    projectId: projectId
  }
  return request({
    url: '/reports/userList',
    method: 'post',
    data
  })
}

// 为报告添加用户
export function addUser(projectId, userList) {
  const data = {
    projectId: projectId,
    userList: userList
  }
  return request({
    url: '/reports/addUser',
    method: 'post',
    data
  })
}

// 为报告添加用户
export function deleteReports(projectId) {
  const data = {
    projectId: projectId
  }
  return request({
    url: '/reports/deleteReports',
    method: 'post',
    data
  })
}