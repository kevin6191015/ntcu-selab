<template>
  <el-container class="home-container">
    <!--頂部-->
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo">😀</span>
      <span class="head-title">DashBoard</span>
      <el-avatar
        icon="el-icon-user-solid"
        style="color: #222; float: right; padding: 20px"
        >{{username}}</el-avatar>
    </el-header>
    <!-- 主體 -->
    <el-container>
      <!-- 側邊欄 -->
      <el-aside width="13%">
        <el-menu
          :default-active="$route.path"
          router
          text-color='black'
          active-text-color='red'
        >
          <el-menu-item
            v-for="(item, i) in navList"
            :key="i"
            :index="item.name"
          >
            <i :class="item.icon"></i>
            {{item.title }}
          </el-menu-item>
          <el-button type="primary" style="width: 70%;  border: none" round @click='logout'>logout</el-button>
        </el-menu>
      </el-aside>
      <el-main>
        <!--路由佔位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import store from '@/store'
export default {
  name: 'Home',
  data () {
    return {
      username: store.state.user.username,
      navList: [
        {name: '/home', title: '首頁', icon: 'el-icon-s-home'},
        {name: '/user', title: '使用者管理', icon: 'el-icon-s-custom'}
      ]
    }
  },
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

<style >
.nav-logo {
  position: absolute;
  padding-top: -1%;
  left: 5%;
  font-size: 40px;
}

.head-title {
  position: absolute;
  padding-top: 20px;
  left: 15%;
  font-size: 20px;
  font-weight: bold;
}
</style>
