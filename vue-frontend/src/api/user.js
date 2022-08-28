import request from '@/utils/request'
import store from '../store'

export function userLogin (data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function addUser (data) {
  return request({
    url: '/user/addUser',
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function deleteUser (data) {
  return request({
    url: '/user/deleteUser?uid=' + data.id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function updateUser (data) {
  return request({
    url: '/user/updateUser',
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function getUser () {
  return request({
    url: '/user/getUsers',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
