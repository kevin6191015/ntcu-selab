import request from '@/utils/request'

export function userLogin (data) {
  return request({
    url: 'data/login',
    method: 'post',
    data
  })
}
