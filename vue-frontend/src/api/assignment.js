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

export function DeleteAssignment (data) {
  return request({
    url: '/assignment/deleteAssignment?cid=' + data.cid + '&qid=' + data.qid + '&created_time=' + data.created_time,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function updateAssignment (data) {
  return request({
    url: '/assignment/updateAssignment?cid=' + data.cid,
    method: 'post',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}
