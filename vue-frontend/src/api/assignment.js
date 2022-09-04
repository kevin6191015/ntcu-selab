import request from '@/utils/request'
import store from '../store'

export function getAllAssignments (data) {
  return request({
    url: '/assignment/getAssignments?cid=' + data.cid,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
