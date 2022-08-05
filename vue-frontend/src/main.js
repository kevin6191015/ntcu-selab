// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import store from './store'
Vue.use(ElementUI)
Vue.config.productionTip = false
var axios = require('axios')
Vue.prototype.$ajax = axios

router.beforeEach((to, from, next) => {
  // 路由需要認證
  if (to.meta.requireAuth) {
    // 判斷store裡是否有token
    if (store.state.token !== '') {
      // 取得token中的到期時間
      var base64Url = store.state.token.split('.')[1]
      var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
      var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
      }).join(''))
      var Expiretime = JSON.parse(jsonPayload).exp
      // 取得當前時間
      var now = Math.floor(new Date().getTime() / 1000)
      // 判斷token是否已過期
      if (now < Expiretime) {
        next()
      } else {
        alert('憑證已過期，請重新登入')
        next({
          path: '/login',
          query: {
            redirect: to.fullPath
          }
        })
      }
    } else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
    }
  } else {
    next()
  }
}
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
