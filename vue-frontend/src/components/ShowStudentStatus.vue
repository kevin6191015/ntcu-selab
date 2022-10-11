<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-col class="a1">{{this.git}}</el-col>
      </el-row>
      <el-row>
        <el-col class="a2">{{'學生姓名: ' + this.$store.state.seletedstudent.student_name}}</el-col>
        <el-col class="a3">{{'題目名稱: ' + this.$store.state.assignment.question_name}}</el-col>
      </el-row>
      <el-row>
        <el-table
          :data="tabledata"
          style="width: 100%">
          <el-table-column
            prop="color"
            width="60">
            <template slot-scope="scope">
              <img :src="scope.row.color" width="30px" height="30px"/>
            </template>
          </el-table-column>
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
              <el-button @click="Seesourcecode(scope.row)" type="text" size="small">觀看程式碼</el-button>
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
import { getPersonalScore, getPersonalReport, getGitUrl } from '@/api/score'
export default {
  name: 'ShowStudentStatus',
  data () {
    return {
      tabledata: [],
      git: 'Git Repository: ',
      project_name: this.$store.state.project_name + '_' + this.$store.state.seletedstudent.student_id
    }
  },
  created () {
    getPersonalScore({
      project_name: this.project_name
    }).then(res => {
      let tmp1 = res.data.data['Personal Score']
      getPersonalReport({
        project_name: this.project_name
      }).then(res => {
        for (let i = 0; i < tmp1.length && i < res.data.data['Personal Report'].length; i++) {
          tmp1[i].compile_result = res.data.data['Personal Report'][i].compile_result
          tmp1[i].source_code = res.data.data['Personal Report'][i].source_code
          tmp1[i].report_suggestion = res.data.data['Personal Report'][i].report_suggestion
          if (tmp1[i].unit_test_score <= 60) {
            tmp1[i].color = 'https://i.imgur.com/HJGBB1X.jpg'
          } else if (tmp1[i].unit_test_score > 60 && tmp1[i].unit_test_score <= 90) {
            tmp1[i].color = 'https://i.imgur.com/C9yij3Q.jpg'
          } else if (tmp1[i].unit_test_score > 90) {
            tmp1[i].color = 'https://i.imgur.com/fRUnBEW.jpg'
          }
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
  methods: {
    Seesourcecode (row) {
      let temp = this.$store.state.project_name
      let code = row.source_code
      this.$store.commit('SET_PROJECT_NAME', code)
      let { href } = this.$router.resolve({
        name: 'ShowStudentSourcecode'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('SET_PROJECT_NAME', temp)
    },
    Seequestion2 (row) {
      let temp = this.$store.state.project_name
      let suggestion = row.report_suggestion
      this.$store.commit('SET_PROJECT_NAME', suggestion)
      let { href } = this.$router.resolve({
        name: 'ShowSuggestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('SET_PROJECT_NAME', temp)
    }
  }
}
</script>

<style>
.container2 {
  background-color: rgb(228, 228, 228);
  height: 655px;
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

.el-table .bad-row {
  background: rgba(211, 27, 27, 0.423);
}

.el-table .normal-row {
  background: rgba(214, 197, 15, 0.379);
}

.el-table .good-row {
  background: #52da087d;
}
</style>
