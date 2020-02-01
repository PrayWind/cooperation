import request from "../utils/request.js"

exports function login(username, password) {
    const data = {
        username, password
    }

    return request({
        url: '/login',
        method: 'post',
        data
    })
    
}


