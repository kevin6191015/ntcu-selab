<template>
  <el-container class="home-container">
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo">😀</span>
      <span class="head-title">DashBoard</span>
      <el-dropdown style="float: right;">
        <el-button type="primary" plain>
          {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-button type="text" @click='logout'>logout</el-button>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
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
        </el-menu>
      </el-aside>
      <el-main>
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
      username: store.state.user.name,
      navList: [
        {name: '/home', title: '首頁', icon: 'el-icon-s-home'},
        {name: '/user', title: '使用者管理', icon: 'el-icon-s-custom'},
        {name: '/AddQuestion', title: '新增題目', icon: 'el-icon-s-custom'}
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
