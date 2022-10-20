<template>
  <div id="sitebody">
    <div id="header">
      <div id="space-top"></div>
      <div id="qname-green">
        <h3>題目名稱:{{questionname}}</h3>
      </div>
    </div>
    <div id="content">
      <h5>{{content.data.code}}</h5>
      <div v-show="(content.code == 400)">此題無程式碼</div>
    </div>
    <div id = "footer">
      <el-button  @click='goback'>關閉視窗</el-button>
    </div>
  </div>
</template>
<script>
import {getSourcecode} from '@/api/question'
export default {
  data () {
    return {
      content: [],
      questionname: ''
    }
  },
  created () {
    this.questionname = this.$store.state.Question_To_Show.slice(6)
    getSourcecode(this.questionname).then(res => {
      this.content = res.data
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
    })
  },
  methods: {
    goback () {
      window.close()
    }
  }
}
</script>
<style scoped>
  #sitebody{
    width:100%;
    height: 100%;
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
  #content{
    border: 3px solid rgba(0, 0, 0, 0.397);
    background-color:  rgba(43, 83, 194, 0.514);
    border-radius: 12px;
    padding: 8px;
    margin-top: 10px;
    margin-left:1%;
    margin-right:1%;
    text-align:left;
    white-space: pre;
  }
  #footer{
    clear:both;
    height:80px;
    text-align:right;
    line-height:80px;
    margin-right:2%;
  }
  </style>
