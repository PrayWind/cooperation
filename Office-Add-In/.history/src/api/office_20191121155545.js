import request from '../utils/request'

export function login(data) {
    const data = {
        data
    }

    return request({
      url: '/login',
      method: 'post',
      data
    })
  }