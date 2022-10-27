<template>

  <body id="login-page">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系統登入</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.loginName" auto-complete="off" placeholder="帳號"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password" auto-complete="off" show-password placeholder="密碼"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 100%;  border: none" @click='login'>登入</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
import { userLogin } from '../api/user'
export default {
  name: 'Login',
  inject: ['reload'],
  data () {
    return {
      loginForm: {
        loginName: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      userLogin({
        username: this.loginForm.loginName,
        password: this.loginForm.password
      })
        .then((resp) => {
          let code = resp.data.code
          if (code === 200) {
            let data = resp.data.data
            let token = data.token
            let user = data.user
            let role = user.role
            this.$store.commit('SET_TOKENN', token)
            this.$store.commit('SET_USER', user)
            if (role === 'ROOT') {
              this.$store.commit('SET_ROLE', '4')
            } else if (role === 'teacher') {
              this.$store.commit('SET_ROLE', '3')
            } else if (role === 'TA') {
              this.$store.commit('SET_ROLE', '2')
            } else if (role === 'student') {
              this.$store.commit('SET_ROLE', '1')
            } else {
              this.$message({
                showClose: true,
                message: 'your format of role is wrong!',
                type: 'warning'
              })
              this.reload()
            }
            this.$router.replace({
              path: '/chooseclass'
            })
          } else {
            this.$message({
              showClose: true,
              message: resp.data.message,
              type: 'warning'
            })
          }
        })
    }
  }
}
</script>

<style scoped>
#login-page {
  background: url("../assets/img/Login_bg.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

body {
  margin: 0px;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  /*box-shadow: 0 0 25px #cac6c6;*/
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
