// import request from '@/utils/request'
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
