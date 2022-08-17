import request from '@/utils/request'
import store from '../store'

export function AddQuestionbank2 (data) {
  return request({
    url: '/question/addQuestionToBank2',
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function AddSourceocde (data) {
  return request({
    url: '/sourcecode/addSourceCode?question_name=' + data.question_name,
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function ShowQuestion1 () {
  return request({
    url: '/question/getQuestionBank1',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
