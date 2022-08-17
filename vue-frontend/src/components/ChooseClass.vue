<template>
    <el-container>
        <el-header v-if="teacher">
            <h2 class="grid-content bg-purple-dark" align="center">課程選擇(老師)</h2>
        </el-header>
        <el-header v-else>
            <h2 class="grid-content bg-purple-dark" align="center">課程選擇(學生)</h2>
        </el-header>
        <el-header align="center">
            <el-select v-model="sem" placeholder="學期" @change="ChangeSem()">
                <el-option
                v-for="item in all_class"
                :key="item.semester"
                :label="item.semester"
                :value="item.semester">
                </el-option>
            </el-select>
            <el-select v-model="class_name" placeholder="課程名稱" @change="ChangeClass()">
                <el-option
                v-for="item in class_by_sem"
                :key="item.class_name"
                :label="item.class_name"
                :value="item.class_name">
                </el-option>
            </el-select>
        </el-header>
        <el-header align="center">
          <el-button v-for="item in content" :key="item.class_name">
            {{item.semester}} {{item.class_name}}
          </el-button>
        </el-header>
    </el-container>
</template>

<script>
import { getAllCourse, getCourseBySem } from '../api/course'
import store from '@/store'
export default {
  name: 'chooseclass',
  data () {
    return {
      content: [],
      all_class: [],
      class_name: '',
      teacher: true,
      sem: '',
      class_by_sem: []
    }
  },
  created () {
    if (store.state.role === 'student') {
      this.teacher = false
    }
    getAllCourse().then(res => {
      this.all_class = res.data.Courses
      this.content = this.all_class
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
      var path = this.$route.query.redirect
      this.$router.replace({
        path: path === '/' || path === undefined ? '/chooseclass' : path})
    })
  },
  methods: {
    ChangeSem: function () {
      getCourseBySem({sem: this.sem}).then(res => {
        this.class_by_sem = res.data.Courses
        this.content = res.data.Courses
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
        var path = this.$route.query.redirect
        this.$router.replace({
          path: path === '/' || path === undefined ? '/chooseclass' : path})
      })
    },
    ChangeClass: function () {
      let a = this.content
      for (let i = 0; i < a.length; i++) {
        if (a[i].class_name === this.class_name) {
          this.content = []
          this.content = a[i]
        }
      }
      console.log(this.content.TA)
      store.commit('SET_CLASS', this.semester)
    }
  }
}
</script>

<style>
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
    font-size:24px;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
