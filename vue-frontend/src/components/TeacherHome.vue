<template>
  <el-container class="home-container">
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo">😀</span>
      <span class="head-title">DashBoard</span>
      <span class="head-center-title">當前課程: {{class_name}}</span>
      <el-dropdown style="float:right">
        <el-button type="primary" plain>
          {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-button type="text" @click='logout'>登出</el-button>
          </el-dropdown-item>
          <el-dropdown-item>
            <el-button type="text" @click='chooseCourse'>選擇課程</el-button>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
      <el-aside width="15%">
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
  name: 'TeacherHome',
  data () {
    return {
      username: store.state.user.name,
      class_name: store.state.class,
      navList: [
        {name: '/teacherhome', title: '首頁', icon: 'el-icon-s-home'},
        {name: '/AddQuestion', title: '新增題目', icon: 'el-icon-document'},
        {name: '/SelectQuestion', title: '選擇題目', icon: 'el-icon-document'},
        {name: '/SystemAccount', title: '系統帳號管理', icon: 'el-icon-s-custom'},
        {name: '/CourseAccount', title: '課程帳號管理', icon: 'el-icon-s-custom'},
        {name: '/ShowHomework', title: '顯示作業', icon: 'el-icon-s-custom'}
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
    },
    chooseCourse () {
      var path = this.$route.query.redirect
      this.$router.replace({
        path: path === '/' || path === undefined ? '/chooseclass' : path})
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

.head-center-title {
  position: absolute;
  padding-top: 20px;
  left: 50%;
  font-size: 20px;
  font-weight: bold;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}

.el-icon-arrow-down {
    font-size: 13px;
}
</style>
