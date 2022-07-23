<template>
  <div class= "login" >
      <h3> 登錄 </h3>
      用戶名 : <input type="text" v-model="loginForm.loginName" placeholder="請輸入用户名"/>
      <br><br>
      密碼 ：  <input type="password" v-model="loginForm.password" placeholder="請輸入密碼"/>
      <br><br>
      <button> 登錄</button>
  </div>
</template>

<script>

export default {
  name: 'Login',
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
  }
}
</script>
