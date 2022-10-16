import Vue from 'vue'
import Router from 'vue-router'

import TeacherHome from '@/components/TeacherHome'
import StudentHome from '@/components/StudentHome'
import Login from '@/components/Login'
import ChooseClass from '@/components/ChooseClass'
import CourseAccount from '@/components/CourseAccount'
import ShowHomework from '@/components/ShowHomework'
import AddQuestion from '@/components/AddQuestion'
import SelectQuestion from '@/components/SelectQuestion'
import ShowQuestion from '@/components/ShowQuestion'
import SystemAccount from '@/components/SystemAccount'
import ShowSourcecode from '@/components/ShowSourcecode'
import ShowStudentSourcecode from '@/components/ShowStudentSourcecode'
import PublishAssignment from '@/components/PublishAssignment'
import ShowCourseStudent from '@/components/ShowCourseStudent'
import ShowStudentStatus from '@/components/ShowStudentStatus'
// import ShowAssignemnt from '@/components/ShowAssignment'
import ShowSuggestion from '@/components/ShowSuggestion'
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
            title: '系統帳號管理',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/SystemAccount',
          name: 'SystemAccount',
          component: SystemAccount,
          meta: {
            requireAuth: true,
            Role: true,
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
            title: '發布題目',
            prevName: 'teacherhome'
          }
        },
        {
          path: '/ShowCourseStudent',
          name: 'ShowCourseStudent',
          component: ShowCourseStudent,
          meta: {
            requireAuth: true,
            Role: true,
            title: '學生一覽',
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
            title: '學生做題狀況一覽',
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
      name: 'StudentHome',
      component: StudentHome,
      meta: {
        requireAuth: true
      }
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
