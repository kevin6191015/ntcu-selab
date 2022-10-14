<template>
  <div>
    <el-row>
      <el-menu
        default-active="/ShowAssignment"
        router
        style="width:100%"
        background-color="#545c64"
        mode="horizontal"
        text-color='#fff'
        :unique-opened="true"
        active-text-color='#ffd04b'>
        <el-menu-item index="/ShowAssignment">
          <span class="head-title">Dashboard</span>
        </el-menu-item>
        <el-submenu index="/AddQuestion">
          <template slot="title">題目管理</template>
          <el-menu-item index="/AddQuestion">新增題目</el-menu-item>
          <el-menu-item index="/PublishAssignment">發布題目</el-menu-item>
        </el-submenu>
        <el-submenu index="/SystemAccount">
          <template slot="title">帳號管理</template>
          <el-menu-item index="/SystemAccount">系統帳號管理</el-menu-item>
          <el-menu-item index="/CourseAccount">課程帳號管理</el-menu-item>
        </el-submenu>
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
              <!-- <el-dropdown-item>
                <el-button type="text" style="color:#545c64" @click='chooseCourse'>選擇課程</el-button>
              </el-dropdown-item> -->
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
        <el-menu-item style="float:right" index="/ChooseClass">
          <span class="head-center-title">當前課程: {{this.$store.state.class}}</span>
        </el-menu-item>
      </el-menu>
    </el-row>
    <el-row>
      <!-- <el-breadcrumb class="bread" separator-class="el-icon-arrow-right">
        <transition-group name="breadcrumb">
          <el-breadcrumb-item v-for="(item,index) in breadList" :key="item.path">
            <span v-if="item.redirect==='noRedirect'||index==breadList.length-1" class="no-redirect">{{ item.meta.title }}</span>
            <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
          </el-breadcrumb-item>
        </transition-group>
      </el-breadcrumb> -->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
        <el-breadcrumb-item
        v-for="(item,index) in breadList"
        :key="index"
        :to="{path: item.path}">
        {{item.meta.title}}
      </el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row class="but">
      <el-button type="primary" onClick="history.back( );return true;">123</el-button>
    </el-row>
    <el-row>
      <router-view></router-view>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'TeacherHome',
  data () {
    return {
      breadList: []
    }
  },
  watch: {
    $route () {
      this.getBreadcrumb()
    }
  },
  methods: {
    getBreadcrumb () {
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title && item.name !== 'Home')
      this.breadList = matched.filter(item => item.meta && item.meta.title && item.name !== 'Home')
    },
    isDashboard (route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Home'.toLocaleLowerCase()
    },
    handleLink (item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(this.pathCompile(path))
    },
    logout () {
      this.$store.commit('REMOVE_INFO')
      this.$message({
        showClose: true,
        message: '登出成功',
        type: 'success'
      })
      this.$router.replace({
        path: '/login'
      })
    },
    chooseCourse () {
      var path = this.$route.query.redirect
      this.$router.replace({
        path: path === '/' || path === undefined ? '/chooseclass' : path})
    }
  },
  created () {
    this.getBreadcrumb()
  }
}
</script>

<style >
.head-title {
  font-size: 20px;
  color: #fff;
  font-weight: bold;
}

.head-center-title {
  left: 50%;
  font-size: 20px;
  font-weight: bold;
}

.bread {
  background-color: rgb(228, 228, 228);
  font-size: 12px;
  padding: 10px
}

.but {
  background-color: rgb(228, 228, 228);
}
</style>
