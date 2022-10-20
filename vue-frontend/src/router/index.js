import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/view/Login'
import ChooseClass from '@/view/ChooseClass'

import TeacherHome from '@/view/teacher/TeacherHome'
import CourseAccount from '@/view/teacher/CourseAccount'
import ShowHomework from '@/view/teacher/ShowHomework'
import AddQuestion from '@/view/teacher/AddQuestion'
import SelectQuestion from '@/view/teacher/SelectQuestion'
import ShowQuestion from '@/view/teacher/ShowQuestion'
import SystemAccount from '@/view/teacher/SystemAccount'
import ShowSourcecode from '@/view/teacher/ShowSourcecode'
import ShowStudentSourcecode from '@/view/teacher/ShowStudentSourcecode'
import PublishAssignment from '@/view/teacher/PublishAssignment'
import ShowCourseStudent from '@/view/teacher/ShowCourseStudent'
import ShowStudentStatus from '@/view/teacher/ShowStudentStatus'
import DeleteQuestion from '@/view/teacher/DeleteQuestion'
import ShowSuggestion from '@/view/teacher/ShowSuggestion'
import UpdateAssignment from '@/view/teacher/UpdateAssignment'

import StudentHome from '@/view/student/StudentHome'
import ShowStudentHomework from '@/view/student/ShowStudentHomework'
import ShowYourStatus from '@/view/student/ShowYourStatus'
import 'bootstrap/dist/css/bootstrap.min.css'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/chooseclass',
      name: 'ChooseClass',
      component: ChooseClass,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/',
      name: 'Default',
      component: Login
    },
    {
      path: '/teacherhome',
      name: 'teacherhome',
      component: TeacherHome,
      meta: {
        requireAuth: true,
        Role: true,
        Auth: '2',
        title: '首頁',
        prevName: null
      },
      children: [
        {
          path: '/AddQuestion',
          name: 'AddQuestion',
          component: AddQuestion,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '3',
            title: '新增題目',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/SelectQuestion',
          name: 'SelectQuestion',
          component: SelectQuestion,
          meta: {
            requireAuth: true,
            Role: true,
            title: '選擇題目',
            prevName: 'PublishAssignment',
            Auth: '2'
          }
        },
        {
          path: '/SystemAccount',
          name: 'SystemAccount',
          component: SystemAccount,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '4',
            title: '系統帳號管理',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/CourseAccount',
          name: 'CourseAccount',
          component: CourseAccount,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '2',
            title: '課程帳號管理',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/ShowHomework',
          name: 'ShowHomework',
          component: ShowHomework,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '2',
            title: '題目一覽',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/PublishAssignment',
          name: 'PublishAssignment',
          component: PublishAssignment,
          meta: {
            requireAuth: true,
            Role: true,
            title: '發布作業',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/UpdateAssignment',
          name: 'UpdateAssignment',
          component: UpdateAssignment,
          meta: {
            requireAuth: true,
            Role: true,
            title: '更新作業',
            Auth: '2',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/DeleteQuestion',
          name: 'DeleteQuestion',
          component: DeleteQuestion,
          meta: {
            requireAuth: true,
            Role: true,
            title: '刪除題目',
            prevName: 'teacherhome',
            Auth: '3'
          }
        },
        {
          path: '/ShowCourseStudent',
          name: 'ShowCourseStudent',
          component: ShowCourseStudent,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '2',
            title: '班級一覽',
            prevName: 'ShowHomework'
          }
        },
        {
          path: '/ShowStudentStatus',
          name: 'ShowStudentStatus',
          component: ShowStudentStatus,
          meta: {
            requireAuth: true,
            Role: true,
            Auth: '2',
            title: '學生做題狀況',
            prevName: 'ShowCourseStudent'
          }
        }
        // {
        //   path: '/ShowAssignment',
        //   name: 'ShowAssignment',
        //   component: ShowAssignemnt,
        //   meta: {
        //     requireAuth: true,
        //     Role: true,
        //     title: '首頁',
        //     prevName: null
        //   }
        // }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/studenthome',
      name: 'studenthome',
      component: StudentHome,
      meta: {
        requireAuth: true,
        title: '首頁',
        prevName: null
      },
      children: [
        {
          path: '/ShowStudentHomework',
          name: 'ShowStudnetHomework',
          component: ShowStudentHomework,
          meta: {
            requireAuth: true,
            title: '題目一覽',
            prevName: 'studenthome'
          }
        },
        {
          path: '/ShowYourStatus',
          name: 'ShowYourStatus',
          component: ShowYourStatus,
          meta: {
            requireAuth: true,
            title: '做題狀況',
            prevName: 'ShowStudnetHomework'
          }
        }
      ]
    },
    {
      path: '/ShowQuestion',
      name: 'ShowQuestion',
      component: ShowQuestion,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/ShowSourcecode',
      name: 'ShowSourcecode',
      component: ShowSourcecode,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/ShowStudentSourcecode',
      name: 'ShowStudentSourcecode',
      component: ShowStudentSourcecode,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/ShowSuggestion',
      name: 'ShowSuggestion',
      component: ShowSuggestion,
      meta: {
        requireAuth: true
      }
    }
  ]
})
