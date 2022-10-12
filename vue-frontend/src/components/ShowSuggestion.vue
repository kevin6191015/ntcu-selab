<template>
  <div id="sitebody">
    <div id="header">
      <div id="space-top"></div>
      <div id="qname-green">
        <h3>題目名稱：{{questionname}}</h3>
      </div>
    </div>
    <div id="content">
      <h5>{{suggestion}}</h5>
    </div>
    <div id = "footer">
      <el-button  @click='goback'>關閉視窗</el-button>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      content: [],
      questionname: '',
      suggestion: ''
    }
  },
  created () {
    this.questionname = this.$store.state.assignment.question_name
    this.suggestion = this.$store.state.project_name.replaceAll('|', '\n\n')
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
