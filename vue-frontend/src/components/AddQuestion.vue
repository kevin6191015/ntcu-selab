<template>
  <div >
    <div id="sitebody">
    <div id="header">
      <h3>新增題目</h3>
      <FormulateForm v-model = "QuestionForm">
      <FormulateInput name="question_name" label="題目名稱" validation="required" placeholder="題目名稱" />
      <FormulateInput type="textarea" name="description" label="題目敘述" placeholder="題目敘述" />
      <FormulateInput
      type="url"
      name="image1"
      label="image1"
      placeholder="image1 url"
/>
      <FormulateInput
      type="url"
      name="image2"
      label="image2"
      placeholder="image2 url"
      help="請輸入網址(可將圖片上傳imgur)"
/>
      <FormulateInput  name="input_or_not" type="checkbox" label="有無輸出"/>

      </FormulateForm>
   </div>
    <div v-show="QuestionForm.input_or_not">
    <div id="sidebar_left">
      <FormulateForm v-model = "QuestionForm">
        <FormulateInput name="input1" label="input1" placeholder="input1" />
        <FormulateInput name="input2" label="input2" placeholder="input2" />
        <FormulateInput name="input3" label="input3" placeholder="input3" />
        <FormulateInput name="input4" label="input4" placeholder="input4" />
        <FormulateInput name="input5" label="input5" placeholder="input5" />
        <FormulateInput name="input6" label="input6" placeholder="input6" />
        <FormulateInput name="input7" label="input7" placeholder="input7" />
        <FormulateInput name="input8" label="input8" placeholder="input8" />
        <FormulateInput name="input9" label="input9" placeholder="input9" />
        <FormulateInput name="input10" label="input10" placeholder="input10" />
      </FormulateForm>
    </div>
    <div id="sidebar_right">
      <FormulateForm v-model = "QuestionForm">
        <FormulateInput name="output1" label="output1" placeholder="output1" />
        <FormulateInput name="output2" label="output2" placeholder="output2" />
        <FormulateInput name="output3" label="output3" placeholder="output3" />
        <FormulateInput name="output4" label="output4" placeholder="output4" />
        <FormulateInput name="output5" label="output5" placeholder="output5" />
        <FormulateInput name="output6" label="output6" placeholder="output6" />
        <FormulateInput name="output7" label="output7" placeholder="output7" />
        <FormulateInput name="output8" label="output8" placeholder="output8" />
        <FormulateInput name="output9" label="output9" placeholder="output9" />
        <FormulateInput name="output10" label="output10" placeholder="output10" />
      </FormulateForm>
    </div>
    </div>
    <div id="footer">
      <FormulateForm @submit="questionsumbit">
      <FormulateInput type="submit"  label="提交" />
      </FormulateForm>
    </div>
  </div>
  </div>
</template>

<script>
import { AddQuestionbank2 } from '../api/question'
/* import store from '../store' */
export default {
  name: 'AddQuestion',
  data () {
    return {
      QuestionForm: {
        question_name: '',
        description: '',
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
        input_or_not: false,
        teacher: '',
        class_id: ''
      },
      responseResult: []
    }
  },
  methods: {
    questionsumbit () {
      let output = []
      output[0] = this.QuestionForm.output1
      output[1] = this.QuestionForm.output2
      output[2] = this.QuestionForm.output3
      output[3] = this.QuestionForm.output4
      output[4] = this.QuestionForm.output5
      output[5] = this.QuestionForm.output6
      output[6] = this.QuestionForm.output7
      output[7] = this.QuestionForm.output8
      output[8] = this.QuestionForm.output9
      output[9] = this.QuestionForm.output10
      let input = []
      input[0] = this.QuestionForm.input1
      input[1] = this.QuestionForm.input2
      input[2] = this.QuestionForm.input3
      input[3] = this.QuestionForm.input4
      input[4] = this.QuestionForm.input5
      input[5] = this.QuestionForm.input6
      input[6] = this.QuestionForm.input7
      input[7] = this.QuestionForm.input8
      input[8] = this.QuestionForm.input9
      input[9] = this.QuestionForm.input10

      for (let i = 0; i < input.length; i++) {
        input[i] = input[i] ? input[i] : 'null'
        output[i] = output[i] ? output[i] : 'null'
      }
      let image1 = this.QuestionForm.image1 ? this.QuestionForm.image1 : 'null'
      let image2 = this.QuestionForm.image2 ? this.QuestionForm.image2 : 'null'
      let classid = this.QuestionForm.class_id ? this.QuestionForm.class_id : 'null'
      let teacher = this.QuestionForm.teacher ? this.QuestionForm.teacher : 'null'
      let description = this.QuestionForm.description ? this.QuestionForm.description : 'null'
      let test = this.QuestionForm.input_or_not ? 1 : 0

      let url1 = '/question/addQuestionToBank2?'
      url1 = url1 + 'question_name=' + this.QuestionForm.question_name
      url1 = url1 + '&question_description=' + description
      url1 = url1 + '&image1=' + image1 + '&image2=' + image2

      for (let i = 0; i < input.length; i++) {
        url1 = url1 + '&input' + (i + 1).toString() + '=' + input[i]
        url1 = url1 + '&output' + (i + 1).toString() + '=' + output[i]
      }

      url1 = url1 + '&teacher=' + teacher + '&class_id=' + classid + '&input_or_not=' + test

      /* http://127.0.0.1:8081/question/addQuestionToBank2?question_name=null
      &question_description=null&image1=null&image2=null&input10=null&output10=null&
      input1=null&output1=null&input2=null&output2=null&input3=null&output3=null&
      input4=null&output4=null&input5=null&output5=null&input6=null&output6=null&
      input7=null&output7=null&input8=null&output8=null&input9=null&output9=null&
      input_or_not=0&teacher=null&class_id=null */
      alert(url1)
      AddQuestionbank2({
        url: url1
      })
    }

  }
}
</script>

<style scoped>
#test {
  margin: 0px;
  width: 100%;
}
#sitebody{
  width:100%;
  margin:0 auto;
  font-size:13px;
}
#header{
  width: 90%;
  text-align:center;
  line-height:80px;
  margin-left:5%;
}
#sidebar_left{
  width:42.5%;
  margin-left: 5%;
  margin-right: 2.5%;
  text-align:center;
  line-height:400px;
  float:left;
}
#sidebar_right{
  margin-left: 2.5%;
  margin-right: 5%;
  width:42.5%;
  text-align:center;
  line-height:400px;
  float:right;
}
#content{
  margin-left:120px;
  margin-right:120px;
  height:400px;
  background-color:#F2FFF2;
  text-align:center;
  line-height:400px;
}
#footer{
  clear:both;
  height:80px;
  margin-right: 10%;
  text-align:center;
  line-height:80px;
  float:right;
}
</style>
