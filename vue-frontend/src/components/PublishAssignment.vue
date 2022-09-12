<template>
  <div>
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="title-orange">
          <h3>以選擇題目</h3>
        </div>
      </div>
      <div id="content1">
      <el-table
        :data="questions"
        stripe
        tooltip-effect="dark"
        style="width: 100%"
      >
        <el-table-column type="index" label="序號" ></el-table-column>
        <el-table-column prop="question_name" label="題目名稱"></el-table-column>
      </el-table>
      </div>
      <div>
        <div id="footer-left">
          <el-button type="success" @click='addquestion'>自行出題</el-button>
        </div>
        <div id="footer-left">
          <el-button type="success" @click='selectquestion'>題庫選題</el-button>
        </div>
        <div id="footer-right">
          <el-button type="primary" @click='refresh'>重整頁面</el-button>
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
        <el-button type="info" @click='publish'>發布</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import store from '../store'
import {ShowQuestion1, ShowQuestion2} from '../api/question'
import {addAssignment} from '../api/assignment'
export default {
  name: 'PublishAssginment',
  inject: ['reload'],
  data () {
    return {
      temp: [],
      questions: [],
      question_id: [],
      publish_time: '',
      deadline: '',
      id: '',
      run: true
    }
  },
  created () {
    this.id = store.state.selectedQuestion
    ShowQuestion1().then(res => {
      for (let i = 0; i < (this.id.length + 1); i += 6) {
        for (let j = 0; j < res.data.data.Questions.length; j++) {
          if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].question_id) {
            var temp = {question_name: res.data.data.Questions[j].question_name, id: res.data.data.Questions[j].question_id}
            this.questions.push(temp)
            break
          }
        }
      }
    })
    ShowQuestion2().then(res => {
      for (let i = 0; i < (this.id.length + 1); i += 6) {
        for (let j = 0; j < res.data.data.Questions.length; j++) {
          if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].id) {
            var temp = {question_name: res.data.data.Questions[j].question_name, id: res.data.data.Questions[j].id}
            this.questions.push(temp)
            break
          }
        }
      }
    })
  },
  methods: {
    publish () {
      let temp1 = Date.parse(this.publish_time)
      let date1 = new Date(temp1 + 28800000)
      let publishtime = date1.toISOString().slice(0, 4) + date1.toISOString().slice(5, 7) + date1.toISOString().slice(8, 10)
      let temp2 = Date.parse(this.deadline)
      let date2 = new Date(temp2 + 28800000)
      let deadline = date2.toISOString().slice(0, 4) + date2.toISOString().slice(5, 7) + date2.toISOString().slice(8, 10)
      let cid = store.state.class_id
      for (let x in this.questions) {
        addAssignment({
          qid: this.questions[x].id,
          cid: cid,
          release_time: publishtime,
          deadline: deadline
        })
      }
    },
    selectquestion () {
      this.$router.replace({
        path: '/Selectquestion'
      })
    },
    addquestion () {
      this.$router.replace({
        path: '/AddQuestion'
      })
    },
    refresh () {
      location.reload()
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
    background-color: rgb(224, 132, 57);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
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
    margin-left: 5%;
    width:fit-content;
    padding: 1px;
    text-align:left;
    background-color: rgb(127, 185, 121);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    float: left
  }
  #footer-right{
    margin-top: 10px;
    margin-right: 5%;
    width:fit-content;
    padding: 1px;
    text-align:left;
    background-color: rgba(43, 83, 194, 0.514);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    float: right
  }
  #footer-single{
    margin-top: 20px;
    margin-left: 5%;
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
    margin-right:5%;
  }
  </style>
