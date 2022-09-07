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
          <el-button type="success" @click='publish'>自行出題</el-button>
        </div>
        <div id="footer-left">
          <el-button type="success" @click='publish'>題庫選題</el-button>
        </div>
      </div>
      <div id="footer-single">
        <span class="demonstration">發布時間:</span>
        <el-date-picker
          v-model="value1"
          type="date"
          placeholder="發布時間"
          format="yyyy 年 MM 月 dd 日">
        </el-date-picker>
        <span class="demonstration">截止時間:</span>
        <el-date-picker
          v-model="value2"
          type="date"
          placeholder="截止時間"
          format="yyyy 年 MM 月 dd 日">
        </el-date-picker>
      </div>
      <div id="footer">
        <el-button type="info" @click='publish'>發布</el-button>
      </div>
      {{value1}}
    </div>
  </div>
</template>
<script>
import store from '../store'
import {ShowQuestion1, ShowQuestion2} from '../api/question'
import { id } from 'html-webpack-plugin/lib/chunksorter'
export default {
  data () {
    return {
      temp: [],
      questions: [],
      value1: '',
      value2: '',
      id,
      run: true
    }
  },
  created () {
    this.run = true
    this.id = store.state.selectedQuestion
    ShowQuestion1().then(res => {
      this.content1 = JSON.parse(JSON.stringify(res.data.data.Questions))
      for (let i = 0; i < (this.id.length + 1); i += 6) {
        for (let j = 0; j <= res.data.data.Questions.length; j++) {
          if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].question_id) {
            var temp = {question_name: res.data.data.Questions[j].question_name}
            this.questions.push(temp)
            break
          }
        }
      }
    })
    ShowQuestion2().then(res => {
      this.content2 = JSON.parse(JSON.stringify(res.data.data.Questions))
      for (let i = 0; i < (this.id.length + 1); i += 6) {
        for (let j = 0; j <= res.data.data.Questions.length; j++) {
          if (this.id.slice((i), (i + 5)) === res.data.data.Questions[j].question_id) {
            var temp = {question_name: res.data.data.Questions[j].question_name}
            this.questions.push(temp)
            break
          }
        }
      }
    })
  },
  methods: {
    getfrombank1 () {
      ShowQuestion1().then(res => {
        this.content1 = JSON.parse(JSON.stringify(res.data.data.Questions))
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    },
    getfrombank2 () {
      ShowQuestion2().then(res => {
        this.content2 = JSON.parse(JSON.stringify(res.data.data.Questions))
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    },
    selectd1 (i) {
      for (let j = 0; j <= this.content1.length; j++) {
        if (this.id.slice((i), (i + 5)) === this.content1[j].question_id) {
          alert(this.id.slice((i), (i + 5)))
          var temp = {question_name: this.content1[j].question_name}
          this.questions.push(temp)
        }
      }
    },
    selectd2 (i) {
      for (let j = 0; j <= this.content2.length; j++) {
        if (this.id.slice((i), (i + 5)) === this.content2[j].question_id) {
          var temp = {question_name: this.content2[j].question_name}
          this.questions.push(temp)
        }
      }
    },
    publish () {
      window.close()
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
