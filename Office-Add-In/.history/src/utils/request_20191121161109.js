export default service() {
  // create an axios instance
  const service = axios.create({
    baseURL: "http://localhost:8089/addIn", // api的base_url
    timeout: 20000 // request timeout
  })

  // request interceptor
  service.interceptors.request.use(config => {
    // Do something before request is sent

    config.headers['X-Token'] = "测试Token" // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改

    return config
  }, error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  })

  // respone interceptor
  service.interceptors.response.use(
    response => {
      const res = response.data
      console.log(res);

    }, error => {
      console.log('err' + error)// for debug
      Message({
        message: '登录连接超时（后台不能连接，请联系系统管理员）',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    })
}
