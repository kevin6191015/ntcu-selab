<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-col class="title1">題目名稱 : {{this.$store.state.assignment.question_name}}</el-col>
        <el-col class="title2">
          <el-button @click="SeeCourseStatus()">
            查看班級作答資訊
          </el-button>
          <el-button @click="Seequestion()">
            觀看題目
          </el-button>
        </el-col>
      </el-row>
      <div style="margin:10px"></div>
      <el-row>
        <el-table
          :data="tableData"
          stripe
          highlight-current-row
          @current-change="seleted"
          style="width: 100%">
          <el-table-column
            prop="student_name"
            label="學生姓名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="submit_times"
            label="作答次數"
            width="180">
          </el-table-column>
          <el-table-column
            prop="unit_tes_score"
            label="作答分數">
          </el-table-column>
          <el-table-column
            prop="analysis_date"
            label="繳交時間">
          </el-table-column>
        </el-table>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getLatestScore } from '@/api/personalscore'
import { getStudent } from '@/api/student'
export default {
  name: 'ShowCourseStudent',
  data () {
    return {
      tableData: [],
      student: [],
      project_name: this.$store.state.project_name
    }
  },
  created () {
    getStudent({
      class_id: this.$store.state.class_id
    }).then(res => {
      this.student = res.data.data.Students
      getLatestScore({
        project_name: this.project_name
      }).then(res => {
        let tmp = res.data.data.Scores
        for (let i = 0; i < tmp.length; i++) {
          for (let j = 0; j < this.student.length; j++) {
            if (tmp[i].student_id === this.student[j].student_id) {
              tmp[i].student_name = this.student[j].student_name
              break
            }
          }
        }
        this.tableData = tmp
      })
    })
  },
  methods: {
    seleted (val) {
      let student = {
        student_id: val.student_id,
        student_name: val.student_name
      }
      this.$store.commit('SET_SELETED_STUDENT', student)
      this.$router.replace({
        path: '/ShowStudentStatus'})
    },
    Seequestion () {
      let assignment = this.$store.state.assignment
      this.$store.commit('SET_QUESTION_TO_SHOW', assignment.question_id + ',' + assignment.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    SeeCourseStatus () {
      let { href } = this.$router.resolve({
        name: 'ShowCourseStatus'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
    }
  }
}
</script>

<style>
.container2 {
  background-color: #EDEDED;
  height: 685px;
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

.title1 {
  width: auto;
  float:left;
  font-size: 20px;
  padding: 10px;
  border: 4px solid rgba(0, 0, 0, 0.397);
  border-radius: 12px;
  font-family: "Microsoft YaHei";
}

.title2 {
  width: auto;
  float:right;
  font-size: 20px;
  padding: 10px;
}
</style>
