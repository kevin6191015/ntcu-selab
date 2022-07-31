import request from '@/utils/request'
import store from '../store'

export function getStudent (data) {
  return request({
    url: 'data/student/getStudents?id=1',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}
