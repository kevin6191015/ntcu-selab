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
    Question_To_Show: sessionStorage.getItem('Question_To_Show'),
    assignment: JSON.parse(sessionStorage.getItem('assignment')),
    seletedstudent: JSON.parse(sessionStorage.getItem('seletedstudent')),
    project_name: sessionStorage.getItem('project_name')
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
    SET_ASSIGNMENT: (state, assignment) => {
      state.assignment = assignment
      sessionStorage.setItem('assignment', JSON.stringify(assignment))
    },
    SET_SELETED_STUDENT: (state, seletedstudent) => {
      state.assignment = seletedstudent
      sessionStorage.setItem('seletedstudent', JSON.stringify(seletedstudent))
    },
    SET_PROJECT_NAME: (state, projectname) => {
      state.assignment = projectname
      sessionStorage.setItem('project_name', projectname)
    },
    REMOVE_INFO: (state) => {
      state.token = ''
      state.user = {
      }
      state.role = ''
      state.selectedQuestion = ''
      state.class = ''
      state.class_id = ''
      state.assignment = {
      }
      state.seletedstudent = {
      }
      state.project_name = ''
      sessionStorage.setItem('token', '')
      sessionStorage.setItem('user', JSON.stringify(''))
      sessionStorage.setItem('role', '')
      sessionStorage.setItem('class', '')
      sessionStorage.setItem('class_id', '')
      sessionStorage.setItem('selectedQuestion', '')
      sessionStorage.setItem('assignment', JSON.stringify(''))
      sessionStorage.setItem('seletedstudent', JSON.stringify(''))
      sessionStorage.setItem('project_name', '')
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
