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
    imagelink: JSON.parse(sessionStorage.getItem('imagelink')),
    sourcecode: JSON.parse(sessionStorage.getItem('sourcecode')),
    project_name: JSON.parse(sessionStorage.getItem('project_name')),
    controlreload: JSON.parse(sessionStorage.getItem('controlreload')),
    add_question_mode: JSON.parse(sessionStorage.getItem('add_question_mode'))
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
      state.seletedstudent = seletedstudent
      sessionStorage.setItem('seletedstudent', JSON.stringify(seletedstudent))
    },
    SET_IMAGELINK: (state, imagelink) => {
      state.imagelink = imagelink
      sessionStorage.setItem('imagelink', JSON.stringify(imagelink))
    },
    SET_SOURCECODE: (state, sourcecode) => {
      state.sourcecode = sourcecode
      sessionStorage.setItem('sourcecode', JSON.stringify(sourcecode))
    },
    SET_PROJECT_NAME: (state, projectname) => {
      state.project_name = projectname
      sessionStorage.setItem('project_name', JSON.stringify(projectname))
    },
    SET_CONTROLRELOAD: (state, controlreload) => {
      state.controlreload = controlreload
      sessionStorage.setItem('controlreload', JSON.stringify(controlreload))
    },
    SET_ADD_QUESTION_MODE: (state, addquestionmode) => {
      state.add_question_mode = addquestionmode
      sessionStorage.setItem('add_question_mode', JSON.stringify(addquestionmode))
    },
    REMOVE_INFO: () => {
      sessionStorage.removeItem('token')
      sessionStorage.removeItem('user')
      sessionStorage.removeItem('role')
      sessionStorage.removeItem('class')
      sessionStorage.removeItem('class_id')
      sessionStorage.removeItem('selectedQuestion')
      sessionStorage.removeItem('project_name')
      sessionStorage.removeItem('Question_To_Show')
      sessionStorage.removeItem('imagelink')
      sessionStorage.removeItem('sourcecode')
      sessionStorage.removeItem('controlreload')
      sessionStorage.removeItem('assignment')
      sessionStorage.removeItem('seletedstudent')
      sessionStorage.removeItem('add_question_mode')
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
