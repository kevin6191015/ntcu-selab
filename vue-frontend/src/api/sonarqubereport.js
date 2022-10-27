import request from '@/utils/request'
import store from '../store'

export function getSonarqubeReport (data) {
  return request({
    url: '/SonarqubeReport/getSonarqubeReport?project_name=' + data.project_name,
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + store.state.token
    }
  })
}
