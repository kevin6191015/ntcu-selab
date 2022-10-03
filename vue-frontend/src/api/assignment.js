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
    url: '/assignment/addAssignment?cid=' + data.cid + '&qid=' + data.qid + '&release_time=' + data.release_time + '&deadline=' + data.deadline + '&assignment_name=' + data.assignment_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
