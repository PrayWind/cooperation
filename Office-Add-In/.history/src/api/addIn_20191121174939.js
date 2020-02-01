import request from "../utils/request.js"

module.export function login(username, password) {
    const data = {
        username, password
    }

    return request({
        url: '/login',
        method: 'post',
        data
    })
}


