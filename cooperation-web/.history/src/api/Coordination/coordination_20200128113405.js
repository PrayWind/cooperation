
export function getProjectList(search) {
  const data = { search }
  return request.request({
    url: '/coordination/getProjectList',
    method: 'post',
    data
  })
}

export function getAssignmentList(id, currentPage, pageSize, search) {
  const data = {
    id,
    currentPage,
    pageSize,
    search
  }
  return request.request({
    url: '/coordination/getProjectTaskList',
    method: 'post',
    data
  })
}

export function deleteAssignment(id) {
  const data = { id }
  return request.request({
    url: '/coordination/deleteProjectTaskList',
    method: 'post',
    data
  })
}

export function saveProject(data) {
  return request.request({
    url: '/coordination/saveProject',
    method: 'post',
    data
  })
}

export function listDepartmentUser(data) {
  return request.request({
    url: '/coordination/listDepartmentUser',
    method: 'post',
    data
  })
}

export function addProjectUser(data) {
  return request.request({
    url: '/coordination/addProjectUser',
    method: 'post',
    data
  })
}

export function getProjectUserList(id) {
  const data = { id }
  return request.request({
    url: '/coordination/getProjectUserList',
    method: 'post',
    data
  })
}

export function saveProjectTask(form) {
  const data = { form }
  return request.request({
    url: '/coordination/saveProjectTask',
    method: 'post',
    data
  })
}
