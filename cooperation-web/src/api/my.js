import http from "@/utils/httpRequest"

export function getMyInfo() {
  return http({
    url: http.adornUrl('/sys/user/info'),
    method: 'get',
    params: http.adornParams()
  })
}

export function saveMyInfo(info) {
  return http({
    url: http.adornUrl('/my/update'),
    method: 'post',
    data: http.adornData(info)
  })
}

export function uploadAvatar(file) {
  const data = file

  return http({
    url: http.adornUrl('/my/uploadAvatar'),
    method: 'post',
    data
  })
}