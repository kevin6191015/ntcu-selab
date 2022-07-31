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
axios.default.baseURL = 'http://127.0.0.1:8081/data'

router.beforeEach((to, from, next) => {
  // 路由需要認證
  if (to.meta.requireAuth) {
    // 判斷store裡是否有token
    if (store.state.token) {
      next()
    } else {
      next({
        path: 'login',
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
