import request from '@/utils/request'
import store from '../store'

export function getStudent (data) {
  return request({
    url: '/student/getStudents?id=' + data.class_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function addStudent (data) {
  return request({
    url: '/student/addStudent?cid=' + data.class_id + '&uid=' + data.user_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function deleteStudent (data) {
  return request({
    url: '/student/deleteStudent?cid=' + data.class_id + '&uid=' + data.user_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
