export function getUserInfo() {
  return request({
    url: '/my/getUserInfo',
    method: 'post',
  })
}

export function saveUserInfo(info) {
  const data = {
    info
  }
  return request({
    url: '/my/saveUserInfo',
    method: 'post',
    data
  })
}

export function savePassword(form) {
  const data = {
    form
  }
  return request({
    url: '/my/savePassword',
    method: 'post',
    data
  })
}

export function uploadAvatar(file) {
  const data = file
  return request({
    url: '/my/uploadAvatar',
    method: 'post',
    data
  })
}