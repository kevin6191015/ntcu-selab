<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-button class="button1" @click="change">已發布</el-button>
        <el-button class="button1" @click="change">未發布</el-button>
      </el-row>
      <div style="margin:10px"></div>
      <el-row>
        <el-table
          v-if="debuted"
          :data="debuted_list"
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
        </el-table>
      </el-row>
      <el-row>
        <el-table
          v-if="not_debuted"
          :data="not_debuted_list"
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
        </el-table>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getAllAssignments } from '@/api/assignment'
import { getAnswered, getCorrect } from '@/api/score'
import { getStudent } from '@/api/student'
import store from '../store'
export default {
  name: 'ShowHomework',
  data () {
    return {
      tableData: [],
      answered: [],
      correct: [],
      student: [],
      debuted_list: [],
      not_debuted_list: [],
      debuted: true,
      not_debuted: false
    }
  },
  created () {
    getStudent({
      class_id: store.state.class_id
    }).then(res => {
      this.student = res.data.data.Students
    })
    getAnswered({
      semester: store.state.class.substring(0, 5),
      class_id: store.state.class_id
    }).then(res => {
      this.answered = res.data.data.Answered
      getCorrect({
        semester: store.state.class.substring(0, 5),
        class_id: store.state.class_id
      }).then(res => {
        this.correct = res.data.data.Correct
        getAllAssignments({
          cid: store.state.class_id
        }).then(res => {
          let tmp = res.data.data.Assignments
          for (let i = 0; i < tmp.length; i++) {
            tmp[i].answered = this.answered[i].people_answered + '/' + this.student.length
            tmp[i].correct = this.correct[i].people_correct
            tmp[i].release_time = tmp[i].release_time.substring(0, 4) + '/' + tmp[i].release_time.substring(4, 6) + '/' + tmp[i].release_time.substring(6, 8)
            tmp[i].deadline = tmp[i].deadline.substring(0, 4) + '/' + tmp[i].deadline.substring(4, 6) + '/' + tmp[i].deadline.substring(6, 8)
            let time = parseInt(tmp[i].release_time.substring(0, 4)) * 12 + parseInt(tmp[i].release_time.substring(5, 7)) * 30 + parseInt(tmp[i].release_time.substring(8, 10))
            var Today = new Date()
            let now = parseInt(Today.getFullYear()) * 12 + parseInt(Today.getMonth() + 1) * 30 + parseInt(Today.getDate())
            console.log('now: ' + now)
            console.log('time: ' + time)
            if (now >= time) {
              this.debuted_list.push(tmp[i])
            } else {
              this.not_debuted_list.push(tmp[i])
            }
          }
          console.log(this.debuted_list)
        })
      })
    })
  },
  methods: {
    seleted_class (seleted) {
      store.commit('SET_ASSIGNMENT', seleted)
      this.$router.replace({
        path: '/ShowCourseStudent'})
    },
    change () {
      this.debuted = !this.debuted
      this.not_debuted = !this.not_debuted
    }
  }
}
</script>

<style>
.container2 {
  width: 1250px;
  margin: 0 auto;
  background-color: rgba(111, 122, 144, 0.555);
  height: 650px;
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
}
</style>
