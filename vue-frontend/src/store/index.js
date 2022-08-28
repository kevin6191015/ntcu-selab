import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem('token'),
    user: JSON.parse(sessionStorage.getItem('user')),
    role: sessionStorage.getItem('role'),
    class: sessionStorage.getItem('class'),
    class_id: sessionStorage.getItem('class_id'),
    selectedQuestion: sessionStorage.getItem('selectedQuestion'),
    Question_To_Show: sessionStorage.getItem('Question_To_Show')
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
    SET_CLASS_ID: (state, Classid) => {
      state.class_id = Classid
      sessionStorage.setItem('class_id', Classid)
    },
    REMOVE_INFO: (state) => {
      state.token = ''
      state.user = {
      }
      state.role = ''
      state.selectedQuestion = ''
      state.class = ''
      state.class_id = ''
      sessionStorage.setItem('token', '')
      sessionStorage.setItem('user', JSON.stringify(''))
      sessionStorage.setItem('role', '')
      sessionStorage.setItem('class', '')
      sessionStorage.setItem('class_id', '')
      sessionStorage.setItem('selectedQuestion', '')
    },
    SET_SELECTEDQUESTION: (state, selectedQuestion) => {
      state.selectedQuestion = selectedQuestion
      sessionStorage.setItem('selectedQuestion', selectedQuestion)
    },
    REMOVE_SELECTEDQUESTION: () => {
      sessionStorage.setItem('selectedQuestion', '')
    },
    SET_QUESTION_TO_SHOW: (state, questiontoshow) => {
      state.Question_To_Show = questiontoshow
      sessionStorage.setItem('Question_To_Show', questiontoshow)
    },
    REMOVE_QUESTION_TO_SHOW: () => {
      sessionStorage.setItem('Question_To_Show', '')
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
