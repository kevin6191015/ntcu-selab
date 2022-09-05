<template>
  <div>
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="title-orange">
          <h3>以選擇題目</h3>
        </div>
      </div>
      <div>
        {{content1[1]}}
        {{content2}}
      </div>
      <div id="footer">
        <el-button  @click='goback'>關閉視窗</el-button>
      </div>
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
      content1: [],
      content2: [],
      questions: [],
      id
    }
  },
  created () {
    this.getfrombank1()
    this.id = store.state.selectedQuestion
  },
  beforeUpdated () {
    this.getfrombank2()
    let i
    for (i = 0; i <= this.id.length; i += 6) {
      if (id.charAt(i) === 'a') {
        slec
      } else {

      }
    }
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
        if (id.slice( (i), (i + 5) === this.content1[j].question_id)) {
          alert('good')
        }
      }
    },
    goback () {
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
    background-color: rgba(111, 122, 144, 0.555);
  }
  #header{
    height:65px;
    text-align:center;
    line-height:65px;
  }
  #space-top{
    height:10px;
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
    margin-left:1%;
    width:90%;
    padding: 5px;
    text-align:center;
    background-color: rgb(224, 132, 57);
    border: 3px solid rgba(0, 0, 0, 0.397);
    border-radius: 12px;
    margin-left: 5%;
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
  #footer{
    clear:both;
    height:80px;
    text-align:right;
    line-height:80px;
    margin-right:2%;
  }
  </style>
