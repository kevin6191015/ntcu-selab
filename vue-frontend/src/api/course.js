import request from '@/utils/request'
import store from '../store'

export function getAllCourse () {
  return request({
    url: '/course/getAllCourses',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getCourseBySem (data) {
  return request({
    url: '/course/getCoursesBySemester?semester=' + data.sem,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}

export function getSemester () {
  return request({
    url: '/course/getSemester',
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
