import request from '@/utils/request'
import store from '../store'

export function getClassScore (data) {
  return request({
    url: '/ClassScore/getClassScore?semester=' + data.semester + '&class_id=' + data.class_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getAnsweredEveryday (data) {
  return request({
    url: '/ClassScore/getAnsweredEveryday?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getEveryRange (data) {
  return request({
    url: '/ClassScore/getEveryRange?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
