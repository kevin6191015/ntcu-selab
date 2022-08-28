<template>
  <div>
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="qname-green">
          <h3>題目名稱:{{QuestionForm.question_name}}</h3>
        </div>
      </div>
      <div id="content">
        <h3>題目敘述:{{QuestionForm.image1}}</h3>
        <el-image
          style="width: 100px; height: 100px"
          src= "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
          fit = "contain"></el-image>
      </div>
        <h3>Values:</h3>
        {{content}}
        <h3>Values:</h3>
        {{QuestionForm}}
      <div id="footer">footer
        <el-button  @click='goback'>確認</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {ShowSelectedQuestion1, ShowSelectedQuestion2} from '../api/question'
import store from '../store'
export default {
  data () {
    return {
      QuestionForm: {
        question_name: '',
        question_description: '',
        image1: '',
        image2: '',
        input1: '',
        input2: '',
        input3: '',
        input4: '',
        input5: '',
        input6: '',
        input7: '',
        input8: '',
        input9: '',
        input10: '',
        output1: '',
        output2: '',
        output3: '',
        output4: '',
        output5: '',
        output6: '',
        output7: '',
        output8: '',
        output9: '',
        output10: '',
        input_or_not: false
      },
      content: []
    }
  },
  created () {
    let id = store.state.Question_To_Show
    if (id.charAt(0) === 'a') {
      ShowSelectedQuestion1(id).then(res => {
        this.content = res.data.data
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    } else {
      ShowSelectedQuestion2(id).then(res => {
        this.content = res.data.data
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    }
  },
  beforeUpdate () {
    this.QuestionForm.question_name = this.content.question_name.replace('"', '')
    this.QuestionForm.question_description = this.content.question_description
    this.QuestionForm.image1 = this.content.image1
    this.QuestionForm.image2 = this.content.image2
    this.QuestionForm.input1 = this.content.input1
    this.QuestionForm.input2 = this.content.input2
    this.QuestionForm.input3 = this.content.input3
    this.QuestionForm.input4 = this.content.input4
    this.QuestionForm.input5 = this.content.input5
    this.QuestionForm.input6 = this.content.input6
    this.QuestionForm.input7 = this.content.input7
    this.QuestionForm.input8 = this.content.input8
    this.QuestionForm.input9 = this.content.input9
    this.QuestionForm.input10 = this.content.input10
    this.QuestionForm.output1 = this.content.output1
    this.QuestionForm.output2 = this.content.output2
    this.QuestionForm.output3 = this.content.output3
    this.QuestionForm.output4 = this.content.output4
    this.QuestionForm.output5 = this.content.output5
    this.QuestionForm.output6 = this.content.output6
    this.QuestionForm.output7 = this.content.output7
    this.QuestionForm.output8 = this.content.output8
    this.QuestionForm.output9 = this.content.output9
    this.QuestionForm.output10 = this.content.output10
    this.QuestionForm.input_or_not = this.content.input_or_not
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
  margin:0 auto;
  font-size:20px;
  background-color: rgb(111, 122, 144);
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
  margin-left:2%;
  width:fit-content;
  padding: 1px;
  text-align:left;
  background-color: rgb(127, 185, 121);
}
#sidebar_left{
  width:120px;
  height:400px;
  text-align:center;
  line-height:400px;
  float:left;
}
#sidebar_right{
  width:120px;
  height:400px;
  text-align:center;
  line-height:400px;
  float:right;
}
#content{
  border: 3px solid rgb(43, 60, 214);
  background-color:  rgb(43, 160, 214);
  border-radius: 12px;
  padding: 5px;
  margin-left:2%;
  margin-right:2%;
  height: 100%;
  text-align:left;
  line-height:400px;
}
#footer{
  clear:both;
  height:80px;
  text-align:center;
  line-height:80px;
}
</style>
