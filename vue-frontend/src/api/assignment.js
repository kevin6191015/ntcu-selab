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

export function addAssignment (data) {
  return request({
    url: '/assignment/addAssignment?cid=' + data.cid,
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}
