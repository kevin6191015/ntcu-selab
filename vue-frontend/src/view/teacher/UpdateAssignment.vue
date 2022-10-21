<template>
  <div id="container">
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="title-orange">
          <h3>更新作業</h3>
        </div>
      </div>
      <div id="content1">
      <el-table
        :data="assignments"
        stripe
        tooltip-effect="dark"
        style="width: 100%"
      >
        <el-table-column type="index" label="序號" ></el-table-column>
        <el-table-column prop="question_name" label="題目名稱"></el-table-column>
        <el-table-column
          fixed="right"
          label="題目預覽"
        >
          <template slot-scope="scope">
            <el-button @click="SeeQuestion(scope.row)" type="text" size="small">查看該題</el-button>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="刪除題目"
        >
          <template slot-scope="scope">
            <el-button @click="deleteQuestion(scope.row)" type="text" size="small">刪除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </div>
      <div>
        <div id="footer-right">
          <h5>作業名稱:</h5>
        </div>
        <div id="footer-right-no-margin">
          <el-input v-model="assignment_name" clearable placeholder="請輸入該次作業名稱"></el-input>
        </div>

        <div id="footer-left">
          <el-button type="success" @click='addquestion'>自行出題</el-button>
        </div>
        <div id="footer-left-no-margin">
          <el-button type="success" @click='revisequestion'>修改考古</el-button>
        </div>
        <div id="footer-left-no-margin">
          <el-button type="success" @click='selectquestion'>題庫選題</el-button>
        </div>
        <div id="footer-left-no-margin-nobackground">
          <h5>新增題目:</h5>
        </div>
      </div>
      <div id="footer-single">
        <span class="demonstration">發布時間:</span>
        <el-date-picker
          v-model="publish_time"
          type="date"
          placeholder="發布時間"
          format="yyyy 年 MM 月 dd 日">
        </el-date-picker>
        <span class="demonstration">截止時間:</span>
        <el-date-picker
          v-model="deadline"
          type="date"
          placeholder="截止時間"
          format="yyyy 年 MM 月 dd 日">
        </el-date-picker>
      </div>
      <div id="footer">
        <el-button type="info" @click='update'>更新</el-button>
      </div>
    </div>
    <div v-show="notshow">
      {{newdata}}
      {{newrealse_time}}
      {{newdeadline}}
    </div>
  </div>
</template>
<script>
import store from '@/store'
import {ShowQuestion1, ShowQuestion2} from '@/api/question'
import {addAssignment, getAllAssignments, DeleteAssignment, updateAssignment} from '@/api/assignment'
// 5 for select question,6 for add question, 7 for revise old question, 8 for add question for assignment in upadte mode
export default {
  name: 'PublishAssginment',
  inject: ['reload'],
  data () {
    return {
      temp: [],
      questions: [],
      question_id: [],
      assignments: [],
      publish_time: '',
      deadline: '',
      id: '',
      assignment_name: '',
      notshow: false,
      error_or_not: false
    }
  },
  created () {
    getAllAssignments({
      cid: this.$store.state.class_id
    }).then(res => {
      let tmp = res.data.data.Assignments
      for (let i = 0; i < tmp.length; i++) {
        if (tmp[i].assignment_name === this.$store.state.publishedQuestion) {
          this.assignments.push(tmp[i])
          // console.log(this.publish_time)
        }
      }
      this.assignment_name = this.assignments[0].assignment_name
      let temp = new Date(this.assignments[0].release_time.slice(0, 4), this.assignments[0].release_time.slice(4, 6) - 1, this.assignments[0].release_time.slice(6, 8))
      this.publish_time = temp
      let temp2 = new Date(this.assignments[0].deadline.slice(0, 4), this.assignments[0].deadline.slice(4, 6) - 1, this.assignments[0].deadline.slice(6, 8))
      this.deadline = temp2
    })
    this.initialize()
  },
  computed: {
    newdata () {
      if (store.state.controlreload === '1') {
        store.commit('SET_CONTROLRELOAD', '0')
        this.$store.commit('SET_ADD_QUESTION_MODE', '0')
        this.refresh()
      }
      return this.$store.state.selectedQuestion
    },
    newdeadline () {
      let deadlinetemp = Date.parse(this.deadline)
      let publishtemp = Date.parse(this.publish_time)
      if (publishtemp > deadlinetemp) {
        this.$message({
          showClose: true,
          message: '截止時間不能早於發布時間',
          type: 'warning'
        })
        this.set_deadline(this.publish_time)
      }
      return this.deadline
    }
  },
  methods: {
    update () {
      if (this.publish_time === '' || this.deadline === '') {
        this.warning()
      } else {
        let temp1 = Date.parse(this.publish_time)
        let date1 = new Date(temp1 + 28800000)
        let publishtime = date1.toISOString().slice(0, 4) + date1.toISOString().slice(5, 7) + date1.toISOString().slice(8, 10)
        let temp2 = Date.parse(this.deadline)
        let date2 = new Date(temp2 + 28800000)
        let deadline = date2.toISOString().slice(0, 4) + date2.toISOString().slice(5, 7) + date2.toISOString().slice(8, 10)
        let cid = store.state.class_id
        if (this.assignment_name === '') {
          this.$message({
            showClose: true,
            message: '請輸入此次作業名稱',
            type: 'warning'
          })
          return
        } else if (this.assignments.length === 0) {
          this.$message({
            showClose: true,
            message: '請選擇題目',
            type: 'warning'
          })
          return
        }
        console.log(this.assignments.length)
        for (let x in this.assignments) {
          console.log(this.assignments[x].assignment_name)
          if (this.assignments[x].assignment_name) {
            updateAssignment({
              assignmentName: this.assignment_name,
              name: this.assignments[x].question_name,
              id: this.assignments[x].question_id,
              cid: cid,
              releaseTime: publishtime,
              createdTime: this.assignments[x].created_time,
              deadLine: deadline
            })
              .then((resp) => {
                let code = resp.data.code
                if (code === 200) {
                  this.error_or_not = false
                } else {
                  this.error_or_not = true
                }
              })
          } else {
            addAssignment({
              assignmentName: this.assignment_name,
              name: this.assignments[x].question_name,
              id: this.assignments[x].question_id,
              cid: cid,
              releaseTime: publishtime,
              createdTime: publishtime,
              deadLine: deadline
            })
              .then((resp) => {
                let code = resp.data.code
                if (code === 200) {
                  this.error_or_not = false
                } else {
                  this.error_or_not = true
                }
              })
          }
        }
        if (this.error_or_not) {
          this.$message({
            showClose: true,
            message: '更新失敗',
            type: 'warning'
          })
          return
        } else {
          this.$message({
            showClose: true,
            message: '更新成功',
            type: 'success'
          })
          this.id = ''
          this.assignments = []
          this.publish_time = ''
          this.deadline = ''
          this.assignment_name = ''
        }
        store.commit('REMOVE_SELECTEDQUESTION')
        store.commit('SET_ADD_QUESTION_MODE', '0')
        // store.commit('SET_CONTROLRELOAD', '1')
      }
    },
    selectquestion () {
      store.commit('SET_ADD_QUESTION_MODE', '5')
      this.$router.replace({
        path: '/Selectquestion'
      })
    },
    addquestion () {
      store.commit('SET_ADD_QUESTION_MODE', '6')
      this.$router.replace({
        path: '/AddQuestion'
      })
    },
    revisequestion () {
      store.commit('SET_ADD_QUESTION_MODE', '7')
      this.$router.replace({
        path: '/SelectQuestion'
      })
    },
    deleteQuestion (row) {
      if (row.assignment_name) {
        let temp = []
        for (let x in this.assignments) {
          if (row.question_id !== this.assignments[x].question_id) {
            temp.push(this.assignments[x])
          }
        }
        DeleteAssignment({
          cid: this.$store.state.class_id,
          qid: row.question_id,
          created_time: row.created_time
        })
          .then((resp) => {
            let code = resp.data.code
            if (code === 200) {
              this.$message({
                showClose: true,
                message: '刪除成功',
                type: 'success'
              })
            }
          })
        this.assignments = temp
      } else {
        let temp = []
        for (let x in this.assignments) {
          if (row.question_id !== this.assignments[x].question_id) {
            temp.push(this.assignments[x])
          }
        }
        this.assignments = temp
        console.log('good')
      }
    },
    refresh () {
      location.reload()
    },
    warning () {
      this.$notify({
        title: '警告',
        message: '請選擇日期',
        type: 'warning'
      })
    },
    initialize () {
      this.id = store.state.selectedQuestion
      ShowQuestion1().then(res => {
        for (let i = 0; i < (this.id.length + 1); i += 6) {
          for (let j = 0; j < res.data.data.Questions.length; j++) {
            if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].question_id) {
              var temp = {question_name: res.data.data.Questions[j].question_name, question_id: res.data.data.Questions[j].question_id}
              this.questions.push(temp)
              this.assignments.push(temp)
              break
            }
          }
        }
      })
      ShowQuestion2().then(res => {
        for (let i = 0; i < (this.id.length + 1); i += 6) {
          for (let j = 0; j < res.data.data.Questions.length; j++) {
            if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].id) {
              var temp = {question_name: res.data.data.Questions[j].question_name, question_id: res.data.data.Questions[j].id}
              this.questions.push(temp)
              this.assignments.push(temp)
              break
            }
          }
        }
      })
    },
    SeeQuestion (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
    }
  }
}
</script>
<style scoped>
  #sitebody{
    width:100%;
    margin:0 auto;
    font-size:20px;
    /* background-color: rgba(111, 122, 144, 0.555); */
  }
  #header{
    height:65px;
    text-align:center;
    line-height:65px;
    margin-bottom: 20px;
  }
  #space-top{
    height:10px;
    width: 100%;
  }
  #qname-green{
    margin-left:1%;
    width:fit-content;
    padding: 5px;
    text-align:left;
    background-color: rgb(127, 185, 121);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
  }
  #title-orange{
    width:90%;
    padding: 5px;
    text-align:center;
    margin-left: 5%;
  }
  #content1{
    margin-left: 5%;
    width:90%;
    border: 3px solid rgba(0, 0, 0, 0.397);
  }
  #content{
    border: 3px solid rgba(0, 0, 0, 0.397);
    background-color:  rgba(43, 83, 194, 0.514);
    border-radius: 12px;
    padding: 8px;
    margin-top: 10px;
    margin-left:1%;
    margin-right:1%;
    text-align:left;
    white-space: pre-line;
  }
  #footer-left{
    margin-top: 10px;
    margin-right: 8%;
    width:fit-content;
    padding: 1px;
    text-align:left;
    background-color: rgb(127, 185, 121);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    float: right;
  }
  #footer-left-no-margin{
    margin-top: 10px;
    margin-right: 2%;
    width:fit-content;
    padding: 1px;
    text-align:left;
    background-color: rgb(127, 185, 121);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    float: right;
  }
  #footer-left-no-margin-nobackground{
    text-align:left;
    float: right;
    margin-top: 20px;
    margin-right: 2%;
  }
  #footer-right{
    margin-top: 17px;
    margin-left: 5%;
    margin-right: 4px;
    width:fit-content;
    padding: 1px;
    text-align:left;
    float: left;
  }
  #footer-right-no-margin{
    margin-top: 10px;
    margin-right: 8%;
    width:fit-content;
    padding: 1px;
    text-align:left;
    float: left;
  }
  #footer-single{
    margin-top: 20px;
    margin-left: 5%;
    margin-right: 8%;
    width:90%;
    padding: 1px;
    text-align:left;
    float: left
  }
  #footer{
    clear:both;
    height:80px;
    text-align:right;
    line-height:80px;
    margin-right:8%;
  }
  #container{
    background-color: rgb(228, 228, 228);
    height: 615px;
  }
  </style>
