import axios from 'axios'
import store from '@/store'
import router from '@/router'

const service = axios.create({
  baseURL: process.env.BASE_API
})

// request 請求攔截
service.interceptors.request.use(
  config => {
    if (store.getters.getToken) {
      config.headers['token'] = window.sessionStorage.getItem('token')
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// response響應攔截
axios.interceptors.response.use(response => {
  let res = response.data
  console.log(res)
  if (res.code === 200) {
    return response
  } else {
    return Promise.reject(response.data.msg)
  }
},
error => {
  console.log(error)
  if (error.response.data) {
    error.message = error.response.data.msg
  } else {
    router.push('/login')
  }
  return Promise.reject(error)
}
)

export default service
