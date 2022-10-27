import request from '@/utils/request'
import store from '../store'

export function getLatestScore (data) {
  return request({
    url: '/PersonalScore/getLatestScore?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getPersonalScore (data) {
  return request({
    url: '/PersonalScore/getPersonalScore?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getGitUrl (data) {
  return request({
    url: '/PersonalScore/getGitUrl?username=' + data.username + '&project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
