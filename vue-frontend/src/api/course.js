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

export function AddCourse (data) {
  return request({
    url: '/course/addCourse',
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function UpdateCourse (data) {
  return request({
    url: '/course/updateCourse?cid=' + data.cid,
    method: 'post',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}

export function DeleteCourse (data) {
  return request({
    url: '/course/deleteCourse?cid=' + data.cid,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    },
    data
  })
}
