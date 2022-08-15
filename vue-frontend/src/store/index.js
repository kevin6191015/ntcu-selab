import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem('token'),
    user: JSON.parse(sessionStorage.getItem('user')),
    role: 'student',
    class: 1
  },
  mutations: {
    SET_TOKENN: (state, token) => {
      state.token = token
      sessionStorage.setItem('token', token)
    },
    SET_USER: (state, user) => {
      state.user = user
      sessionStorage.setItem('user', JSON.stringify(user))
    },
    SET_ROLE: (state, role) => {
      state.role = role
      sessionStorage.setItem('role', role)
    },
    SET_CLASS: (state, Class) => {
      state.class = Class
      sessionStorage.setItem('class', Class)
    },
    REMOVE_INFO: (state) => {
      state.token = ''
      state.user = {
      }
      state.role = ''
      sessionStorage.setItem('token', '')
      sessionStorage.setItem('user', JSON.stringify(''))
      sessionStorage.setItem('role', '')
    }
  },
  getters: {
  },
  actions: {
  },
  modules: {
  }
}
)
