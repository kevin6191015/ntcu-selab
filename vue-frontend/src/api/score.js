import request from '@/utils/request'
import store from '../store'

export function getLatestScore (data) {
  return request({
    url: '/score/getLatestScore?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getAnswered (data) {
  return request({
    url: '/score/getAnswered?semester=' + data.semester + '&class_id=' + data.class_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getCorrect (data) {
  return request({
    url: '/score/getCorrect?semester=' + data.semester + '&class_id=' + data.class_id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getPersonalScore (data) {
  return request({
    url: '/score/getPersonalScore?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getPersonalReport (data) {
  return request({
    url: '/score/getPersonalReport?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getGitUrl (data) {
  return request({
    url: '/score/getGitUrl?username=' + data.username + '&project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
