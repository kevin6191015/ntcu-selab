<template>
  <div>
    <el-row>
      <el-menu
        default-active="/teacherhome"
        router
        style="width:100%"
        background-color="#545c64"
        mode="horizontal"
        text-color='#fff'
        :unique-opened="true"
        active-text-color='#ffd04b'>
        <el-menu-item index="/teacherhome">
          <span class="head-title">首頁</span>
        </el-menu-item>
        <el-submenu index="/AddQuestion">
          <template slot="title">題目管理</template>
          <el-menu-item index="/AddQuestion" v-if="isTeacher || isRoot">新增題目</el-menu-item>
          <el-menu-item index="/DeleteQuestion" v-if="isTeacher || isRoot">刪除題目</el-menu-item>
          <el-menu-item index="/PublishAssignment">發布作業</el-menu-item>
        </el-submenu>
        <el-submenu index="/SystemAccount">
          <template slot="title">帳號管理</template>
          <el-menu-item index="/SystemAccount" v-if="isRoot">系統帳號管理</el-menu-item>
          <el-menu-item index="/CourseAccount">課程帳號管理</el-menu-item>
        </el-submenu>
        <el-menu-item index="/ManageCourse" v-if="isRoot">課程管理</el-menu-item>
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
    <el-row v-if="isDashboard(this.$router)">
      <ShowAssignment></ShowAssignment>
    </el-row>
    <el-row>
      <router-view></router-view>
    </el-row>
    <!-- <el-row class="but">
      <el-button type="primary" @click="goBack()">上一頁</el-button>
    </el-row> -->
  </div>
</template>

<script>
import ShowAssignment from './ShowAssignment.vue'
export default {
  name: 'TeacherHome',
  data () {
    return {
      breadList: [],
      isRoot: false,
      isTeacher: false,
      routeTable: []
    }
  },
  watch: {
    $route () {
      this.getBreadcrumb()
    }
  },
  methods: {
    getBreadcrumb () {
      if (this.$store.state.add_question_mode === '3' && this.$router.currentRoute.name === 'AddQuestion') {
        this.breadList = []
        let temp = this.$router.currentRoute
        temp.meta.title = '修改現有題目'
        this.breadList.push(temp)
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'SelectQuestion') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'PublishAssignment') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        this.breadList.push(this.$router.options.routes[2])
      } else if (this.$store.state.add_question_mode === '7' && this.$router.currentRoute.name === 'AddQuestion') {
        this.breadList = []
        let temp = this.$router.currentRoute
        temp.meta.title = '修改現有題目'
        this.breadList.push(temp)
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'SelectQuestion') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'UpdateAssignment') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        this.$router.options.routes[2].meta.title = '首頁'
        this.breadList.push(this.$router.options.routes[2])
      } else if (this.$store.state.add_question_mode === '4' && this.$router.currentRoute.name === 'AddQuestion') {
        this.breadList = []
        let temp = this.$router.currentRoute
        temp.meta.title = '自行出題'
        this.breadList.push(temp)
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'PublishAssignment') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        this.$router.options.routes[2].meta.title = '首頁'
        this.breadList.push(this.$router.options.routes[2])
      } else if (this.$store.state.add_question_mode === '6' && this.$router.currentRoute.name === 'AddQuestion') {
        this.breadList = []
        let temp = this.$router.currentRoute
        temp.meta.title = '自行出題'
        this.breadList.push(temp)
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'UpdateAssignment') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        this.$router.options.routes[2].meta.title = '首頁'
        this.breadList.push(this.$router.options.routes[2])
      } else if ((this.$store.state.add_question_mode === '5' || this.$store.state.add_question_mode === '7') && this.$router.currentRoute.name === 'SelectQuestion') {
        this.breadList = []
        let temp = this.$router.currentRoute
        this.breadList.push(temp)
        for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
          if (this.$router.options.routes[2].children[i].name === 'UpdateAssignment') {
            this.breadList.push(this.$router.options.routes[2].children[i])
            break
          }
        }
        this.$router.options.routes[2].meta.title = '首頁'
        this.breadList.push(this.$router.options.routes[2])
      } else {
        this.breadList = []
        let parent = this.$router.currentRoute.meta.prevName
        if (this.$router.currentRoute.name === 'teacherhome') {
          this.$router.currentRoute.meta.title = '首頁'
        }
        this.breadList.push(this.$router.currentRoute)
        while (parent !== null) {
          if (parent === 'teacherhome') {
            if (this.$router.currentRoute.name === 'ShowHomework' || this.$router.currentRoute.name === 'ShowCourseStudent' || this.$router.currentRoute.name === 'ShowStudentStatus') {
              this.$router.options.routes[2].meta.title = this.$store.state.assignment.assignment_name
            } else {
              this.$router.options.routes[2].meta.title = '首頁'
            }
            this.breadList.push(this.$router.options.routes[2])
            break
          } else {
            for (let i = 0; i < this.$router.options.routes[2].children.length; i++) {
              if (this.$router.options.routes[2].children[i].name === parent) {
                parent = this.$router.options.routes[2].children[i].meta.prevName
                this.breadList.push(this.$router.options.routes[2].children[i])
                break
              }
            }
          }
        }
      }
      this.breadList.reverse()
    },
    isDashboard (route) {
      const name = route.currentRoute.name
      if (name !== 'teacherhome') {
        return false
      }
      return true
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
    // goBack () {
    //   let PrevPage = '/' + this.$router.currentRoute.meta.prevName
    //   if (PrevPage === '/null') {
    //     this.$alert('已經在首頁了!')
    //   } else {
    //     this.$router.replace({
    //       path: PrevPage
    //     })
    //   }
    // }
  },
  created () {
    this.getBreadcrumb()
    if (this.$store.state.role === '4') {
      this.isRoot = true
    }
    if (this.$store.state.role === '3') {
      this.isTeacher = true
    }
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

.but {
  background-color: rgb(228, 228, 228);
}
</style>
