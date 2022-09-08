<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-col style="width: 6%; float:right; background-color:brown; font-size: 20px; padding: 5px;">已發布</el-col>
      </el-row>
      <el-row>
        <el-table
          :data="tableData"
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
      student: []
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
          }
          this.tableData = tmp
        })
      })
    })
  },
  methods: {
    seleted_class (seleted) {
      store.commit('SET_ASSIGNMENT', seleted)
      this.$router.replace({
        path: '/ShowCourseStudent'})
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

.el-col1 {
  border-radius: 10px;
  min-height: 36px;
  margin-left: 13%;
}

.grid-content1 {
  min-height: 36px;
  font-size: 30px;
  background-color: orange;
  padding-left: 30%;
}

.grid-content2 {
  min-height: 36px;
  font-size: 30px;
  margin-left: 28.4%;
  width: 72.5%;
}

.grid-content3 {
  font-size: 13px;
  padding-left: 50%
}

</style>
