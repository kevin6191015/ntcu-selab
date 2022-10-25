<template>
  <div>
    <el-row>
      <el-menu
        default-active="/studenthome"
        router
        style="width:100%"
        background-color="#545c64"
        mode="horizontal"
        text-color='#fff'
        :unique-opened="true"
        active-text-color='#ffd04b'>
        <el-menu-item index="/studenthome">
          <span class="head-title">首頁</span>
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
              <!-- <el-dropdown-item>
                <el-button type="text" style="color:#545c64" @click='chooseCourse'>選擇課程</el-button>
              </el-dropdown-item> -->
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
        <el-menu-item style="float:right" index="/ChooseClass">
          <span class="head-center-title">當前課程 : {{this.$store.state.class}}</span>
        </el-menu-item>
      </el-menu>
    </el-row>
    <el-row>
      <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
        <el-breadcrumb-item
        v-for="(item,index) in breadList"
        :key="index"
        :to="{path: item.path}">
        {{item.meta.title}}
      </el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row>
      <router-view></router-view>
    </el-row>
    <el-row v-if="isDashboard(this.$router)">
      <ShowAssignment></ShowAssignment>
    </el-row>
  </div>
</template>

<script>
import ShowAssignment from '@/view/student/ShowStudentAssignment'
export default {
  name: 'studenthome',
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
      this.breadList = []
      let parent = this.$router.currentRoute.meta.prevName
      let routeTable = this.$router.options.routes[4].children
      routeTable.push(this.$router.options.routes[4])
      this.breadList.push(this.$router.currentRoute)
      while (parent !== null) {
        for (let i = 0; i < routeTable.length; i++) {
          if (routeTable[i].name === parent) {
            parent = routeTable[i].meta.prevName
            this.breadList.push(routeTable[i])
            break
          }
        }
      }
      this.breadList.reverse()
    },
    isDashboard (route) {
      const name = route.currentRoute.name
      if (name !== 'studenthome') {
        return false
      }
      return true
    },
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
  },
  created () {
    this.getBreadcrumb()
  },
  components: {
    ShowAssignment
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
  font-size: 20px;
  padding: 10px;
  font-family: "Microsoft YaHei";
}
</style>
