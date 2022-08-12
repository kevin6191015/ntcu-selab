<template>
  <!-- <div id="app">
    <nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="https://ecsb.ntcu.edu.tw/newweb/index.htm">NTCU-SELAB</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link to="/" class="nav-link active" aria-current="page" >Home</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">Login</router-link >
        </li>
        <li class="nav-item">
          <router-link to="/getStudent" class="nav-link" >GetStudent</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/AddQuestion" class="nav-link" >AddQuestion</router-link>
        </li>
        <el-button type="primary" style="width: 100%;  border: none" round @click='logout'>logout</el-button>
      </ul>
    </div>
  </div>
</nav> -->
<router-view/>
    <!-- <router-link to="/">Home</router-link> |
    <router-link to="/login">Login</router-link> |
    <router-link to="/getStudent">getStudent</router-link>
    <router-view/> -->
  <!-- </div> -->
</template>

<script>
import store from '@/store'
export default {
  name: 'App',
  methods: {
    logout () {
      store.commit('REMOVE_INFO', store.state)
      this.$message({
        showClose: true,
        message: '登出成功',
        type: 'success'
      })
      var path = this.$route.query.redirect
      this.$router.replace({
        path: path === '/' || path === undefined ? '/login' : path})
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
