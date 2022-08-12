// import request from '@/utils/request'
import request from '@/utils/request'
import store from '../store'

export function AddQuestionbank2 (data) {
  return request({
    url: data.url,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
  /* return request({
    url: '/questions/addQuestionToBank2',
    method: 'post',
    data
  }) */
}
