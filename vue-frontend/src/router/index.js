import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Home from '@/components/Home'
import Login from '@/components/Login'
import ChooseClass from '@/components/ChooseClass'

// import GetStudent from '@/components/GetStudent'
import AddQuestion from '@/components/AddQuestion'
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
      redirect: '/home',
      component: Home,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/user',
          name: 'User',
          component: () => import('@/components/GetStudent'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/AddQuestion',
          name: 'AddQuestion',
          component: AddQuestion,
          meta: {
            requireAuth: true
          }
        }
      ]
      // redirect: '/index',
      // children:[
      //   {
      //     path:'/index',
      //     name:'Index',
      //     component:() => import('@/views/home/index'),
      //     meta:{
      //       requireAuth:true
      //     }
      //   }
      // ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
    // {
    //   path: '/getStudent',
    //   name: 'GetStudent',
    //   component: GetStudent,
    //   meta: {
    //     requireAuth: true
    //   }
    // }
  ]
})
