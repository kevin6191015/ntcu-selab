<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-col class="a1">{{this.git}}</el-col>
      </el-row>
      <el-row>
        <el-col class="a2">{{getStudentName}}</el-col>
        <el-col class="a3">{{this.assignment_name}}</el-col>
      </el-row>
      <el-row>
        <el-table
          :data="tabledata"
          stripe
          highlight-current-row
          style="width: 100%">
          <el-table-column type="index" label="繳交次序" ></el-table-column>
          <el-table-column
            prop="analysis_date"
            label="繳交時間"
            width="250">
          </el-table-column>
          <el-table-column
            prop="unit_test_score"
            label="單元測試分數"
            width="180">
          </el-table-column>
          <el-table-column
            prop="compile_result"
            label="編譯結果">
          </el-table-column>
          <el-table-column
            prop="code_quality"
            label="品質測試分數">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="觀看程式碼"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion1(scope.row)" type="text" size="small">觀看程式碼</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="建議程式碼"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion2(scope.row)" type="text" size="small">建議程式碼</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import store from '../store'
import { getPersonalScore, getPersonalReport, getGitUrl } from '@/api/score'
export default {
  name: 'ShowStudentStatus',
  data () {
    return {
      tabledata: [],
      git: 'Git Repository: ',
      assignment_name: '',
      project_name: store.state.project_name + '_' + store.state.seletedstudent.student_id
    }
  },
  mounted () {
    getPersonalScore({
      project_name: this.project_name
    }).then(res => {
      let tmp1 = res.data.data['Personal Score']
      getPersonalReport({
        project_name: this.project_name
      }).then(res => {
        for (let i = 0; i < tmp1.length && i < res.data.data['Personal Report'].length; i++) {
          tmp1[i].compile_result = res.data.data['Personal Report'][i].compile_result
        }
        this.tabledata = tmp1
      })
    })
    getGitUrl({
      username: 'root',
      project_name: this.project_name
    }).then(res => {
      this.git = this.git + res.data.data
    })
  },
  created () {
    this.assignment_name = '題目名稱: ' + store.state.assignment.question_name
  },
  methods: {
    Seequestion1 () {},
    Seequestion2 () {}
  },
  computed: {
    getStudentName () {
      return '學生姓名: ' + store.state.seletedstudent.student_name
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

.a1 {
  background-color: aliceblue;
  font-size: 23px;
  font-family: "Microsoft YaHei";
}

.a2 {
  background-color: rgb(57, 146, 57);
  font-size: 23px;
  font-family: "Microsoft YaHei";
  width: 50%;
  border: 4px solid rgba(0, 0, 0, 0.397);
}

.a3 {
  background-color: rgb(57, 146, 57);
  font-size: 23px;
  font-family: "Microsoft YaHei";
  width: 50%;
  display: flex;
  justify-content: right;
  border: 4px solid rgba(0, 0, 0, 0.397);
}
</style>
