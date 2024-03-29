import request from '@/utils/request'
import store from '../store'

export function AddQuestionbank2 (data) {
  return request({
    url: '/question/addQuestionToBank2?publicornot= ' + data.publicornot,
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

export function getSourcecode (questionname) {
  return request({
    url: '/sourcecode/getSourceCode?question_name=' + questionname,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
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

export function ShowQuestion2 () {
  return request({
    url: '/question/getQuestionBank2',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function ShowQuestion2byTeacher (teacher) {
  return request({
    url: '/question/getQuestionsByTeacher?teacher=' + teacher,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function ShowSelectedQuestion1 (id) {
  return request({
    url: '/question/getQuestionFromBank1?id=' + id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function ShowSelectedQuestion2 (id) {
  return request({
    url: '/question/getQuestionFromBank2?id=' + id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function ShowPublicQuestion () {
  return request({
    url: '/question/getPublicQuestion',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getLatestQuestionID () {
  return request({
    url: '/question/getLatestQuestionID',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function DeleteQuestion (id) {
  return request({
    url: '/question/deleteQuestion?id=' + id,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
