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
          style="width: 100%; margin-top: 10px;">
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
            label="程式碼建議"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion2(scope.row)" type="text" size="small">程式碼建議</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <div class="flex">
        <div class="item">
            <canvas id="myChart" class="item" ></canvas>
        </div>
        <div class="item">
            <canvas id="myChart1" class="item" ></canvas>
        </div>
        <div class="item">
            <canvas id="myChart2" class="item" ></canvas>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { getPersonalScore, getPersonalReport, getGitUrl } from '@/api/score'
import Chart from 'chart.js'
export default {
  name: 'ShowStudentStatus',
  data () {
    return {
      tabledata: [],
      git: 'Git Repository: ',
      project_name: this.$store.state.project_name + '_' + this.$store.state.seletedstudent.student_id,
      labels1: [],
      labels2: [],
      labels3: [],
      data1: [],
      data2: [],
      data3: []
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
          tmp1[i].compile_result = res.data.data['Personal Report'][tmp1.length - i - 1].compile_result
          tmp1[i].source_code = res.data.data['Personal Report'][tmp1.length - i - 1].source_code
          tmp1[i].report_suggestion = res.data.data['Personal Report'][tmp1.length - i - 1].report_suggestion
          this.labels1.push(res.data.data['Personal Report'][tmp1.length - i - 1].submit_times)
          this.labels2.push(res.data.data['Personal Report'][tmp1.length - i - 1].submit_times)
          this.labels3.push(res.data.data['Personal Report'][tmp1.length - i - 1].submit_times)
          this.data1.push(res.data.data['Personal Report'][tmp1.length - i - 1].bugs)
          this.data2.push(res.data.data['Personal Report'][tmp1.length - i - 1].vulnerabilities)
          this.data3.push(res.data.data['Personal Report'][tmp1.length - i - 1].code_smells)
          if (tmp1[i].unit_test_score <= 60) {
            tmp1[i].color = 'https://i.imgur.com/HJGBB1X.jpg'
          } else if (tmp1[i].unit_test_score > 60 && tmp1[i].unit_test_score <= 90) {
            tmp1[i].color = 'https://i.imgur.com/C9yij3Q.jpg'
          } else if (tmp1[i].unit_test_score > 90) {
            tmp1[i].color = 'https://i.imgur.com/fRUnBEW.jpg'
          }
        }
        this.tabledata = tmp1
        const ctx = document.getElementById('myChart')
        const ctx1 = document.getElementById('myChart1')
        const ctx2 = document.getElementById('myChart2')
        const data1 = {
          labels: this.labels1.reverse(),
          datasets: [{
            label: '# of bugs',
            data: this.data1.reverse(),
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0
          }]
        }
        const data2 = {
          labels: this.labels2.reverse(),
          datasets: [{
            label: '# of vulnerabilities',
            data: this.data2.reverse(),
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0
          }]
        }
        const data3 = {
          labels: this.labels3.reverse(),
          datasets: [{
            label: '# of code smells',
            data: this.data3.reverse(),
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0
          }]
        }
    const myChart = new Chart(ctx, { //eslint-disable-line
          type: 'line',
          data: data1,
          options: {
            scales: {
              yAxes: [{
                ticks: {
                  min: 0,
                  suggestedMax: 10,
                  beginAtZero: true,
                  stepSize: 1
                }
              }]
            }
          }
        })
    const myChart1 = new Chart(ctx1, { //eslint-disable-line
          type: 'line',
          data: data2,
          options: {
            scales: {
              yAxes: [{
                ticks: {
                  min: 0,
                  suggestedMax: 10,
                  beginAtZero: true,
                  stepSize: 1
                }
              }]
            }
          }
        })
    const myChart2 = new Chart(ctx2, { //eslint-disable-line
          type: 'line',
          data: data3,
          options: {
            scales: {
              yAxes: [{
                ticks: {
                  min: 0,
                  suggestedMax: 10,
                  beginAtZero: true,
                  stepSize: 1
                }
              }]
            }
          }
        })
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
.item {
  background-color: #fff;
  padding: 10px 10px;
  width: 500px;
  display: flex;
  justify-content: center;
}
canvas{
  display: flex;
  align-content: center;
}
.flex{
  display: flex;
  justify-content:space-between;
  width: auto;
  height: auto;
  margin: 40px 0px 20px;
}

.container2 {
  background-color: rgb(228, 228, 228);
  height: 655px;
}

.a1 {
  font-size: 23px;
  font-family: "Microsoft YaHei";
}

.a2 {
  font-size: 23px;
  font-family: "Microsoft YaHei";
  width: 50%;
}

.a3 {
  font-size: 23px;
  font-family: "Microsoft YaHei";
  width: 50%;
  display: flex;
  justify-content: right;
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
