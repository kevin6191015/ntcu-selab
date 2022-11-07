<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-input class="git" :placeholder="this.git" disabled>
          <template slot="prepend">Git Repository : </template>
          <el-tooltip slot="append" effect="dark" placement="top" content="一鍵複製">
            <el-button size="small" icon="el-icon-document-copy"
              v-clipboard:copy="this.git"
              v-clipboard:success="onCopy"
              v-clipboard:error="onError">
            </el-button>
          </el-tooltip>
        </el-input>
        <el-tooltip effect="dark" placement="top">
          <div slot="content">利用 Git Repository 將 GitLab 上的作答模板 clone 到本機，<br/>觀看題目後依規定作答，作答完畢 git commit 到 GitLab ，<br/>稍等 10~20 秒後刷新本頁即可觀看作答狀況</div>
          <el-button type="text" icon="el-icon-question" circle></el-button>
        </el-tooltip>
      </el-row>
      <el-row>
        <el-col class="a2">{{'學生姓名 : ' + this.$store.state.seletedstudent.student_name}}</el-col>
        <el-col class="a3">{{'題目名稱 : ' + this.$store.state.assignment.question_name}}</el-col>
      </el-row>
      <el-row>
        <el-table
          :data="tabledata"
          style="width: 100%;">
          <el-table-column
            prop="color"
            width="60"
            align="center">
            <template slot-scope="scope">
              <img :src="scope.row.color" width="20px" height="20px" />
            </template>
          </el-table-column>
          <el-table-column
            prop="submit_times"
            align="center"
            label="繳交次序"
            width="100">
          </el-table-column>
          <el-table-column
            prop="analysis_date"
            label="繳交時間"
            width="250">
          </el-table-column>
          <el-table-column
            prop="compile_result"
            label="編譯結果">
          </el-table-column>
          <el-table-column
            prop="unit_test_score"
            label="單元測試分數"
            width="180">
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
        <!-- <div class="item">
            <canvas id="myChart1" class="item" ></canvas>
        </div> -->
        <div class="item">
            <canvas id="myChart2" class="item" ></canvas>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { getPersonalScore, getGitUrl } from '@/api/personalscore'
import { getSonarqubeReport } from '../../api/sonarqubereport'
import Chart from 'chart.js'
export default {
  name: 'ShowStudentStatus',
  data () {
    return {
      tabledata: [],
      git: '',
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
      getSonarqubeReport({
        project_name: this.project_name
      }).then(res => {
        for (let i = 0; i < tmp1.length && i < res.data.data['Sonarqube Report'].length; i++) {
          tmp1[i].compile_result = res.data.data['Sonarqube Report'][tmp1.length - i - 1].compile_result
          tmp1[i].source_code = res.data.data['Sonarqube Report'][tmp1.length - i - 1].source_code
          tmp1[i].report_suggestion = res.data.data['Sonarqube Report'][tmp1.length - i - 1].report_suggestion
          tmp1[i].submit_times = res.data.data['Sonarqube Report'][tmp1.length - i - 1].submit_times
          this.labels1.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].submit_times)
          this.labels2.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].submit_times)
          this.labels3.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].submit_times)
          if (tmp1[i].compile_result === 'compile error') {
            this.data1.push('0')
            this.data2.push('0')
            this.data3.push('0')
          } else {
            this.data1.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].bugs)
            this.data2.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].vulnerabilities)
            this.data3.push(res.data.data['Sonarqube Report'][tmp1.length - i - 1].code_smells)
          }
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
        // const ctx1 = document.getElementById('myChart1')
        const ctx2 = document.getElementById('myChart2')
        const data1 = {
          labels: this.labels1.reverse(),
          datasets: [{
            label: 'number of bugs',
            data: this.data1.reverse(),
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0
          }]
        }
        // const data2 = {
        //   labels: this.labels2.reverse(),
        //   datasets: [{
        //     label: '# of vulnerabilities',
        //     data: this.data2.reverse(),
        //     fill: false,
        //     borderColor: 'rgb(75, 192, 192)',
        //     tension: 0
        //   }]
        // }
        const data3 = {
          labels: this.labels3.reverse(),
          datasets: [{
            label: 'number of code smells',
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
                  suggestedMax: 5,
                  beginAtZero: true,
                  stepSize: 1
                }
              }]
            }
          }
        })
        // const myChart1 = new Chart(ctx1, { //eslint-disable-line
        //       type: 'line',
        //       data: data2,
        //       options: {
        //         scales: {
        //           yAxes: [{
        //             ticks: {
        //               min: 0,
        //               suggestedMax: 5,
        //               beginAtZero: true,
        //               stepSize: 1
        //             }
        //           }]
        //         }
        //       }
        //     })
    const myChart2 = new Chart(ctx2, { //eslint-disable-line
          type: 'line',
          data: data3,
          options: {
            scales: {
              yAxes: [{
                ticks: {
                  min: 0,
                  suggestedMax: 5,
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
      this.git = res.data.data
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
      window.open(href, '_blank', 'toolbar=yes, width=1000')
      this.$store.commit('SET_PROJECT_NAME', temp)
    },
    Seequestion2 (row) {
      if (row.compile_result === 'compile error') {
        this.$notify({
          title: '注意!!',
          message: ('i', {style: 'color: teal'}, '請先編譯成功再觀看程式碼建議!')
        })
      } else {
        let temp = this.$store.state.project_name
        let suggestion = row.report_suggestion
        this.$store.commit('SET_PROJECT_NAME', suggestion)
        let { href } = this.$router.resolve({
          name: 'ShowSuggestion'
        })
        window.open(href, '_blank', 'toolbar=yes, width=1000')
        this.$store.commit('SET_PROJECT_NAME', temp)
      }
    },
    onCopy () {
      this.$notify({
        title: '複製成功!!',
        message: ('i', {style: 'color: teal'}, '可以clone題目後開始作答!')
      })
    },
    onError () {
      this.$notify({
        title: '複製失敗!!',
        message: ('i', {style: 'color: teal'}, '好像沒有複製到喔!')
      })
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
  justify-content:space-around;
  width: auto;
  height: auto;
  margin: 40px 0px 20px;
}

.container2 {
  background-color: #EDEDED;
  height: 655px;
}

.a1 {
  font-size: 23px;
  height: 20%;
  font-family: "Microsoft YaHei";
  background: #fff;
  width: auto;
}

.a2 {
  font-size: 23px;
  font-family: "Microsoft YaHei";
  width: auto;
  float:left;
  font-size: 20px;
  padding: 10px;
  border: 4px solid rgba(0, 0, 0, 0.397);
  border-radius: 12px;
  margin-top: 10px;
}

.a3 {
  font-size: 20px;
  margin-top: 10px;
  font-family: "Microsoft YaHei";
  width: auto;
  float: right;
  padding: 10px;
  border: 4px solid rgba(0, 0, 0, 0.397);
  border-radius: 12px;
  margin-bottom: 10px;
}

.item1 {
  width: 6%;
  display: flex;
  justify-content: center;
}

.git {
  width: 61%;
  font-size: 17px;
  font-family: "Microsoft YaHei";
}

.el-input-group__prepend {
  color: black;
  font-size: 20px;
  padding-left: 10px;
  padding-right: 10px;
  font-family: "Microsoft YaHei";
}
</style>
