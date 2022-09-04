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
      redirect: '/teacherhome',
      component: TeacherHome,
      meta: {
        requireAuth: true,
        Role: true
      }
    },
    {
      path: '/teacherhome',
      name: 'Home',
      component: TeacherHome,
      meta: {
        requireAuth: true,
        Role: true
      },
      children: [
        {
          path: '/AddQuestion',
          name: 'AddQuestion',
          component: AddQuestion,
          meta: {
            requireAuth: true,
            Role: true
          }
        },
        {
          path: '/SelectQuestion',
          name: 'SelectQuestion',
          component: SelectQuestion,
          meta: {
            requireAuth: true,
            Role: true
          }
        },
        {
          path: '/SystemAccount',
          name: 'SystemAccount',
          component: SystemAccount,
          meta: {
            requireAuth: true,
            Role: true
          }
        },
        {
          path: '/CourseAccount',
          name: 'CourseAccount',
          component: CourseAccount,
          meta: {
            requireAuth: true,
            Role: true
          }
        },
        {
          path: '/ShowHomework',
          name: 'ShowHomework',
          component: ShowHomework,
          meta: {
            requireAuth: true,
            Role: true
          }
        }
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
    }
  ]
})
