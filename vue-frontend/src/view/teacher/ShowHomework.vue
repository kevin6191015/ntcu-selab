<template>
  <el-container class="container2">
    <el-main>
      <el-row class="title">作業名稱 : {{this.$store.state.assignment.assignment_name}}</el-row>
      <div style="margin:10px"></div>
      <el-row>
        <el-table
          v-loading="loading"
          element-loading-text="拼命加載中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          :data="assignment"
          stripe
          highlight-current-row
          @current-change="seleted_class"
          style="width: 100%">
          <el-table-column
            prop="question_name"
            label="題目名稱"
            width="250">
          </el-table-column>
          <el-table-column
            prop="answered"
            label="作答人數/總人數"
            width="180">
          </el-table-column>
          <el-table-column
            prop="correct"
            label="答對人數">
          </el-table-column>
          <el-table-column
            prop="release_time"
            label="出題日期">
          </el-table-column>
          <el-table-column
            prop="deadline"
            label="截止日期">
          </el-table-column>
          <!-- <el-table-column
            fixed="right"
            label="題目預覽"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion(scope.row)" type="text" size="small">查看</el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getAllAssignments } from '@/api/assignment'
import { getClassScore } from '@/api/classscore'
import { getStudent } from '@/api/student'
export default {
  name: 'ShowHomework',
  data () {
    return {
      classscore: [],
      student: [],
      assignment: [],
      loading: true
    }
  },
  created () {
    getStudent({
      class_id: this.$store.state.class_id
    }).then(res => {
      this.student = res.data.data.Students
    })
    getClassScore({
      semester: this.$store.state.class.substring(0, 5),
      class_id: this.$store.state.class_id
    }).then(res => {
      this.classscore = res.data.data.ClassScore
      getAllAssignments({
        cid: this.$store.state.class_id
      }).then(res => {
        let tmp = res.data.data.Assignments
        for (let i = 0; i < tmp.length; i++) {
          tmp[i].answered = this.classscore[i].people_answered + '/' + this.student.length
          tmp[i].correct = this.classscore[i].people_correct
          tmp[i].release_time = tmp[i].release_time.substring(0, 4) + '/' + tmp[i].release_time.substring(4, 6) + '/' + tmp[i].release_time.substring(6, 8)
          tmp[i].deadline = tmp[i].deadline.substring(0, 4) + '/' + tmp[i].deadline.substring(4, 6) + '/' + tmp[i].deadline.substring(6, 8)
          if (this.$store.state.assignment.assignment_name === tmp[i].assignment_name) {
            this.assignment.push(tmp[i])
          }
        }
        console.log(this.assignment)
      })
    })
    this.loading = false
  },
  methods: {
    seleted_class (seleted) {
      seleted.release_time = seleted.release_time.substring(0, 4) + seleted.release_time.substring(5, 7) + seleted.release_time.substring(8, 10)
      seleted.deadline = seleted.deadline.substring(0, 4) + seleted.deadline.substring(5, 7) + seleted.deadline.substring(8, 10)
      let classID = ''
      for (let i = 0; i < 3 - this.$store.state.class_id.toString().length; i++) {
        classID += '0'
      }
      classID += this.$store.state.class_id.toString()
      let project = seleted.question_id + '_' + classID + '_' + this.$store.state.class.substring(0, 5) + '_' + seleted.created_time
      this.$store.commit('SET_PROJECT_NAME', project)
      this.$store.commit('SET_ASSIGNMENT', seleted)
      this.$router.replace({
        path: '/ShowCourseStudent'})
    },
    change1 () {
      if (!this.debuted) {
        this.debuted = true
        this.not_debuted = false
      }
    },
    change2 () {
      if (!this.not_debuted) {
        this.not_debuted = true
        this.debuted = false
      }
    },
    Seequestion (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    }
  }
}
</script>

<style>
.container2 {
  background-color: #EDEDED;
  height: 655px;
}

.d-flex1 {
  display: flex;
  flex-wrap: wrap;
}

.button1 {
  width: 6%;
  float:right;
  background-color:rgb(77, 42, 165);
  font-size: 20px;
  padding: 5px;
  color: aliceblue;
  margin: 5px;
}

.title {
  width: auto;
  float:left;
  font-size: 20px;
  padding: 10px;
  border: 4px solid rgba(0, 0, 0, 0.397);
  border-radius: 12px;
  margin-bottom: 20px;
  font-family: "Microsoft YaHei";
}
</style>
