export function templetList(projectId, search) {
    const data = {
        search: search,
        projectId: projectId
    }
    return request({
        url: '/reportDetail/templetList',
        method: 'post',
        data
    })
}

export function projectUserList(projectId, templet) {
    const data = {
        projectId: projectId,
        templet: templet
    }
    return request({
        url: '/reportDetail/projectUserList',
        method: 'post',
        data
    })
}

export function sendToUser(projectId, userIds, templet) {
    const data = {
        projectId: projectId,
        userIds: userIds,
        templet: templet
    }
    return request({
        url: '/reportDetail/sendToUser',
        method: 'post',
        data
    })
}

export function indxTree() {
    return request({
        url: '/reportDetail/indxTree',
        method: 'post',
    })
}

// 获取可选择的模板
export function selectTemplet(indxTreeId, projectId) {
    const data = {
        indxTreeId: indxTreeId,
        projectId: projectId
    }
    return request({
        url: '/reportDetail/selectTemplet',
        method: 'post',
        data
    })
}

// 提交选择的模板
export function saveSelectedTemplet(projectId, templetList) {
    const data = {
        projectId: projectId,
        templetList: templetList
    }
    return request({
        url: '/reportDetail/saveSelectedTemplet',
        method: 'post',
        data
    })
}