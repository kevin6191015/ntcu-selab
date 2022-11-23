<template>
  <div id="container">
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="title-orange">
          <h3>發布作業</h3>
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
        <div id="footer-single">
        <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="auto">
          <el-form-item label="作業名稱：" prop="name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </el-form>
        <el-form ref="form" :model="form" :rules="rules" :inline="true" label-width="auto">
          <el-form-item label="發布日期：" prop="publish_time" >
            <el-date-picker type="date" placeholder="選擇日期" v-model="form.publish_time" style="width: 87%;" format="yyyy 年 MM 月 dd 日"></el-date-picker>
          </el-form-item>
         <el-form-item label="截止日期：" prop="deadline">
          <el-date-picker type="date" placeholder="選擇日期" v-model="form.deadline" style="width: 87%;" format="yyyy 年 MM 月 dd 日"></el-date-picker>
          </el-form-item>
        </el-form>
      </div>
        <div id="footer-left">
          <el-button type="success" @click='addquestion'>自行出題</el-button>
        </div>
        <div id="tooltip1">
          <el-tooltip effect="dark" content="可以從題庫選題或修改已有題目" placement="top">
            <el-button type="text"  icon="el-icon-question" circle></el-button>
        </el-tooltip>
        </div>
        <div id="footer-left-no-margin">
          <el-button type="success" @click='selectquestion'>題庫選題</el-button>
        </div>
      </div>
      <div id="footer">
        <el-button type="info" @click='publish'>發布</el-button>
      </div>
    </div>
    <div v-show="notshow">
      {{newdata}}
      {{newrealse_time}}
      {{newdeadline}}
      {{newname}}
    </div>
  </div>
</template>
<script>
import {ShowQuestion1, ShowQuestion2} from '@/api/question'
import {getAllAssignments, addAssignment} from '@/api/assignment'
// add_question_mode: 1 for select question, 2 for add question, 3 for revise old question, 4 for add question for assignment
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
      assignment_name: '',
      notshow: false,
      error_or_not: false,
      samename: false,
      form: {
        name: '',
        publish_time: '',
        deadline: ''
      },
      rules: {
        name: [
          { required: true, message: '請輸入作業名稱', trigger: 'blur' }
        ],
        publish_time: [
          { type: 'date', required: true, message: '請選擇發布日期', trigger: 'change' }
        ],
        deadline: [
          { type: 'date', required: true, message: '請選擇截止日期', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    this.initialize()
  },
  computed: {
    newdata () {
      if (this.$store.state.controlreload === '1') {
        this.$store.commit('SET_CONTROLRELOAD', '0')
        this.$store.commit('SET_ADD_QUESTION_MODE', '0')
        this.refresh()
      }
      return this.$store.state.selectedQuestion
    },
    newrealse_time () {
      let d = new Date()
      let now = Date.parse(d) / 86400000
      let temp1 = Date.parse(this.form.publish_time) / 86400000
      if ((now - temp1) > 1) {
        this.$message({
          showClose: true,
          message: '時間不能早於今天',
          type: 'warning'
        })
        this.set_publish_time(d)
      }
      return this.form.publish_time
    },
    newdeadline () {
      let d = new Date()
      let now = Date.parse(d) / 86400000
      let temp2 = Date.parse(this.form.deadline) / 86400000
      let deadlinetemp = Date.parse(this.form.deadline)
      let publishtemp = Date.parse(this.form.publish_time)
      if (publishtemp > deadlinetemp || (now - temp2) > 1) {
        this.$message({
          showClose: true,
          message: '截止時間不能早於今天或發布時間',
          type: 'warning'
        })
        if (this.form.publish_time) {
          this.set_deadline(this.form.publish_time)
        } else {
          this.set_deadline(d)
        }
      }
      return this.form.deadline
    },
    newname () {
      this.checkname()
      return this.form.name
    }
  },
  methods: {
    publish () {
      if (this.form.publish_time === '' || this.form.deadline === '') {
        this.warning()
      } else {
        let temp1 = Date.parse(this.form.publish_time)
        let date1 = new Date(temp1 + 28800000)
        let publishtime = date1.toISOString().slice(0, 4) + date1.toISOString().slice(5, 7) + date1.toISOString().slice(8, 10)
        let temp2 = Date.parse(this.form.deadline)
        let date2 = new Date(temp2 + 28800000)
        let deadline = date2.toISOString().slice(0, 4) + date2.toISOString().slice(5, 7) + date2.toISOString().slice(8, 10)
        let cid = this.$store.state.class_id
        if (this.form.name === '') {
          this.$message({
            showClose: true,
            message: '請輸入此次作業名稱',
            type: 'warning'
          })
          return
        } else if (this.samename) {
          this.$message({
            showClose: true,
            message: '不能與已有的作業名稱相同',
            type: 'error'
          })
          return
        } else if (this.questions.length === 0) {
          this.$message({
            showClose: true,
            message: '請選擇題目',
            type: 'warning'
          })
          return
        }
        for (let x in this.questions) {
          addAssignment({
            assignmentName: this.form.name,
            name: this.questions[x].question_name,
            id: this.questions[x].id,
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
        if (this.error_or_not) {
          this.$message({
            showClose: true,
            message: '發布失敗',
            type: 'warning'
          })
          return
        } else {
          this.$message({
            showClose: true,
            message: '發布成功',
            type: 'success'
          })
          this.questions = []
          this.id = ''
          this.form.deadline = ''
          this.form.publish_time = ''
          this.form.name = ''
        }
        this.$store.commit('REMOVE_SELECTEDQUESTION')
        this.$store.commit('SET_ADD_QUESTION_MODE', '0')
        // this.$store.commit('SET_CONTROLRELOAD', '1')
      }
    },
    selectquestion () {
      this.$store.commit('SET_ADD_QUESTION_MODE', '1')
      this.$router.replace({
        path: '/Selectquestion'
      })
    },
    addquestion () {
      this.$store.commit('SET_ADD_QUESTION_MODE', '4')
      this.$router.replace({
        path: '/AddQuestion'
      })
    },
    revisequestion () {
      this.$store.commit('SET_ADD_QUESTION_MODE', '3')
      this.$router.replace({
        path: '/SelectQuestion'
      })
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
      this.id = this.$store.state.selectedQuestion
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
      })
    },
    set_publish_time (d) {
      this.form.publish_time = d
    },
    set_deadline (d) {
      this.form.deadline = d
    },
    checkname () {
      this.samename = false
      getAllAssignments({
        cid: this.$store.state.class_id
      }).then(res => {
        let tmp = res.data.data.Assignments
        for (let i = 0; i < tmp.length; i++) {
          if (tmp[i].assignment_name === this.form.name.trim()) {
            this.samename = true
            this.$message({
              showClose: true,
              message: '不能與已有的作業名稱相同',
              type: 'error'
            })
            return
          }
        }
      })
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
    width:fit-content;
    padding: 1px;
    text-align:left;
    background-color: rgb(127, 185, 121);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    float: right;
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
  #tooltip1{
    text-align:left;
    float: right;
    margin-top: 10px;
    margin-right: 1%;
  }
  #footer-single{
    margin-top: 20px;
    margin-left: 5%;
    margin-right: 8%;
    width:50%;
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
    background-color: #EDEDED;
    height: 685px;
  }
  </style>
