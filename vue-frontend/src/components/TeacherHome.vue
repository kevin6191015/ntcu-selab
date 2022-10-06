<template>
  <el-container class="home-container">
    <el-header>
      <el-row>
        <el-menu
          :default-active="$route.path"
          router
          style="width:100%"
          background-color="#545c64"
          mode="horizontal"
          text-color='#fff'
          active-text-color='#ffd04b'>
          <el-menu-item>
            <span class="head-title">DashBoard</span>
          </el-menu-item>
          <el-menu-item
            v-for="(item, i) in navList"
            :key="i"
            :index="item.name">
            <i :class="item.icon"></i>
            {{item.title }}
          </el-menu-item>
          <el-menu-item style="float:right">
            <el-dropdown>
              <el-button style="background-color:aquamarine" >
                {{this.$store.state.user.name[0]}}
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item disabled style="color:#545c64">
                  {{this.$store.state.user.name}}
                </el-dropdown-item>
                <el-dropdown-item divided>
                  <el-button type="text" style="color:#545c64" @click='logout'>登出</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button type="text" style="color:#545c64" @click='chooseCourse'>選擇課程</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-menu-item>
          <el-menu-item style="float:right">
            <span class="head-center-title">當前課程: {{this.$store.state.class}}</span>
          </el-menu-item>
        </el-menu>
      </el-row>
      <el-row>
        <router-view></router-view>
      </el-row>
    </el-header>
  </el-container>
</template>

<script>
export default {
  name: 'TeacherHome',
  data () {
    return {
      navList: [
        {name: '/ShowAssignment', title: '首頁', icon: 'el-icon-s-home'},
        {name: '/AddQuestion', title: '新增題目', icon: 'el-icon-document'},
        {name: '/PublishAssignment', title: '發布題目', icon: 'el-icon-document'},
        {name: '/SystemAccount', title: '系統帳號管理', icon: 'el-icon-s-custom'},
        {name: '/CourseAccount', title: '課程帳號管理', icon: 'el-icon-s-custom'}
      ]
    }
  },
  methods: {
    logout () {
      this.$store.commit('REMOVE_INFO', this.$store.state)
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
.head-title {
  font-size: 20px;
  font-weight: bold;
}

.head-center-title {
  left: 50%;
  font-size: 20px;
  font-weight: bold;
}

</style>
