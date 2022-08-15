import request from '@/utils/request'
import store from '../store'

export function getCourse () {
  return request({
    url: '/course/getCourses',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
