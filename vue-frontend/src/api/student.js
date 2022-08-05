import request from '@/utils/request'
import store from '../store'

export function getStudent (data) {
  return request({
    url: '/student/getStudents?id=' + data.id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
