<template>
    <el-container>
        <el-header v-if="teacher">
            <h2 class="grid-content bg-purple-dark" align="center">課程選擇(老師)</h2>
        </el-header>
        <el-header v-if="student">
            <h2 class="grid-content bg-purple-dark" align="center">課程選擇(學生)</h2>
        </el-header>
        <el-header align="center" v-if="teacher">
            <el-select v-model="sem" placeholder="學期" @change="ChangeSem()">
                <el-option
                v-for="item in all_sem"
                :key="item"
                :label="item"
                :value="item">
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
        <el-header align="center" v-if="student">
            <el-select v-model="sem" placeholder="學期" @change="ChangeSem()">
                <el-option
                v-for="item in all_sem"
                :key="item"
                :label="item"
                :value="item">
                </el-option>
            </el-select>
            <el-select v-model="class_name" placeholder="課程名稱" @change="ChangeClass()">
                <el-option
                v-for="item in student_class"
                :key="item.class_name"
                :label="item.class_name"
                :value="item.class_name">
                </el-option>
            </el-select>
        </el-header>
        <el-header align="center" v-if="teacher">
          <el-button v-for="item in content" :key="item.class_name" @click="changePage()">
            {{item.semester}} {{item.class_name}}
          </el-button>
        </el-header>
        <el-header align="center" v-if="student">
          <el-button v-for="item in student_Allclass" :key="item.class_name" @click="changePage()">
            {{item.semester}} {{item.class_name}}
          </el-button>
        </el-header>
    </el-container>
</template>

<script>
import { getAllCourse, getCourseBySem, getSemester } from '../api/course'
export default {
  name: 'chooseclass',
  data () {
    return {
      content: [],
      all_sem: [],
      all_class: [],
      class_name: '',
      teacher: false,
      student: false,
      sem: '',
      class_by_sem: [],
      student_Allclass: [],
      student_class: [],
      class_id: '',
      teacher_back_up: [],
      student_back_up: []
    }
  },
  created () {
    if (this.$store.state.role === 'student') {
      this.student = true
    } else if (this.$store.state.role === 'teacher' || this.$store.state.role === 'TA' || this.$store.state.role === 'ROOT') {
      this.teacher = true
    }
    getSemester().then(res => {
      this.all_sem = res.data.data.Semester
    })
    getAllCourse().then(res => {
      this.all_class = res.data.data.Courses
      this.content = res.data.data.Courses
      if (this.student) {
        this.student_Allclass = []
        for (let i = 0; i < this.$store.state.user.classes.split(',').length; i++) {
          for (let j = 0; j < this.all_class.length; j++) {
            if (this.all_class[j].class_id === this.$store.state.user.classes.split(',')[i]) {
              this.student_Allclass.push(this.all_class[j])
            }
          }
        }
      }
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
      this.$router.replace({
        path: '/chooseclass'})
    })
  },
  methods: {
    ChangeSem: function () {
      this.class_name = ''
      getCourseBySem({sem: this.sem}).then(res => {
        this.class_by_sem = res.data.data.Courses
        this.content = res.data.data.Courses
        this.teacher_back_up = this.content
        if (this.student) {
          this.student_class = []
          for (let i = 0; i < this.$store.state.user.classes.split(',').length; i++) {
            for (let j = 0; j < this.class_by_sem.length; j++) {
              if (this.class_by_sem[j].class_id === this.$store.state.user.classes.split(',')[i]) {
                this.student_class.push(this.class_by_sem[j])
              }
            }
          }
          this.student_Allclass = this.student_class
          this.student_back_up = this.student_class
        }
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
        this.$router.replace({
          path: '/chooseclass'})
      })
    },
    ChangeClass: function () {
      if (this.teacher) {
        for (let i = 0; i < this.teacher_back_up.length; i++) {
          if (this.teacher_back_up[i].class_name === this.class_name) {
            this.content = [this.teacher_back_up[i]]
            this.class_id = this.content[0].class_id
          }
        }
      } else if (this.student) {
        for (let i = 0; i < this.student_back_up.length; i++) {
          if (this.student_back_up[i].class_name === this.class_name) {
            this.student_Allclass = [this.student_back_up[i]]
            this.class_id = this.student_Allclass[0].class_id
          }
        }
      }
    },
    changePage: function () {
      if (this.sem === '') {
        this.$alert('請先選擇學期', 'ERROR', {
          confirmButtonText: '確定'
        })
      } else if (this.class_name === '') {
        this.$alert('請先選擇課程', 'ERROR', {
          confirmButtonText: '確定'
        })
      } else {
        this.$store.commit('SET_CLASS', this.sem + this.class_name)
        this.$store.commit('SET_CLASS_ID', this.class_id)
        if (this.teacher) {
          this.$router.replace({
            path: '/ShowHomework'})
        } else if (this.student) {
          this.$router.replace({
            path: '/studenthome'})
        }
      }
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
