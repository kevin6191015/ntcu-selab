<template>
  <el-container id="sitebody" :style="{height: this.height}">
    <el-main>
      <el-row id="header">
        <div v-if="this.Revise_Quesition_Mode">
          <h3>修改現有題目</h3>
        </div>
        <div v-else-if="Add_To_Assignment_Mode">
          <h3>自行出題</h3>
        </div>
        <div v-else>
          <h3>新增題目</h3>
        </div>
        <el-input
          placeholder="題目名稱"
          v-model="question_name"
          size = "big"
          clearable>
        </el-input>
        <el-input
          type="textarea"
          autosize
          placeholder="題目敘述"
          v-model="description">
        </el-input>
      </el-row>
      <el-row id = "content">
        <el-table
          ref="singleTable"
          :data="in_output_list"
          highlight-current-row
          style="width: 100%">
        <el-table-column
          type="index"
          width="50"
          label="編號">
        </el-table-column>
        <el-table-column
          property="input"
          label="輸入"
          width="200PX">
          <template slot-scope="scope">
            <el-input clearble type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="scope.row.input" placeholder="清輸入input"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          property="output"
          label="輸出"
          fixed="right">
          <template slot-scope="scope">
            <el-input clearble type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="scope.row.output" placeholder="清輸入output"></el-input>
          </template>
        </el-table-column>
        </el-table>
        <el-button sytle="width:50%;height:50px;backbround:#fff;color:#000" icon="el-icon-plus" @click="add" type="primary">增加輸入、輸出</el-button>
        <el-button sytle="width:50%;height:50px;backbround:#fff;color:#000" icon="el-icon-minus" @click="sub" type="primary">減少輸入、輸出</el-button>
      </el-row>
      <el-row v-if = "this.Revise_Quesition_Mode" id = "temp">
        <h4>圖片預覽:</h4>
        <el-image
          style="width: 450px; height: 300px"
          :src="url"
          :preview-src-list="srcList">
        </el-image>
      </el-row>
      <el-row v-else id = "footer-left">
        <el-upload
          action
          accept="image/jpeg, image/png"
          :on-change="onUploadChange"
          :before-remove="onUploadRemove"
          :on-exceed="handleExceed"
          :auto-upload="false"
          :limit="2"
          :show-file-list="true"
          :file-list="imageList"
          list-type="picture"
        >
        <el-button icon="el-icon-upload" type="warning" plain>圖片上傳</el-button>
        </el-upload>
      </el-row>
      <el-row id ="footer-right">
        <el-upload
          action
          :on-change="onfileUploadChange"
          :before-remove="onfileUploadRemove"
          :on-exceed="handlefileExceed"
          :limit="1"
          :auto-upload="false"
          :show-file-list="false"
          :file-list="fileList"
        >
          <el-button icon="el-icon-upload" type="warning" plain>程式碼上傳</el-button>
        </el-upload>
        <el-input clearble type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="sourcecode" placeholder="程式碼內容"></el-input>
      </el-row>
      <el-row id="footer">
        <el-switch
          style="display: block"
          v-model="publicbank"
          active-text="放入公共題庫"
          inactive-text="不放入公共題庫">
        </el-switch>
        <el-button type="success" icon="el-icon-s-check" @click='questionsumbit'>確認</el-button>
      </el-row>
      <el-row v-show="notshow">
        {{newsourcecode}}
        {{newname}}
        {{question_name}}
      </el-row>
    </el-main>
  </el-container>
</template>

<script scoped>
import { AddQuestionbank2, AddSourceocde, ShowSelectedQuestion1, ShowSelectedQuestion2, getLatestQuestionID, ShowQuestion2 } from '@/api/question'
export default {
  name: 'AddQuestion',
  data () {
    return {
      question_name: '',
      description: '',
      imageList: [],
      fileList: [],
      image: [],
      code: '',
      object: {
        input: '',
        output: ''
      },
      in_output_list: [{
        input: '',
        output: ''
      }],
      input_num: 1,
      sourcecode: '',
      notshow: false,
      hassouececode: false,
      Revise_Quesition_Mode: false,
      Add_To_Assignment_Mode: false,
      publicbank: false,
      samename: false,
      srcList: [],
      url: '',
      height: ''
    }
  },
  created () {
    console.log(document.body.scrollHeight - 101)
    if (document.body.scrollHeight - 102 < 0) {
      this.height = (window.innerHeight - 42 - 50 - 10) + 'px'
    } else {
      this.height = (document.body.scrollHeight - 101) + 'px'
    }
    if (this.$store.state.add_question_mode === '3' || this.$store.state.add_question_mode === '7') {
      this.Revise_Quesition_Mode = true
      let id = this.$store.state.Question_To_Show
      if (id.charAt(0) === 'a') {
        ShowSelectedQuestion1(id.slice(0, 5)).then(res => {
          let content = res.data.data
          this.question_name = content.question_name
          this.description = content.question_description
          this.input_num = content.inputornot
          this.url = content.image1
          let temp = content.image1
          this.srcList.push(temp)
          temp = content.image2
          this.srcList.push(temp)
          let templist = []
          temp = {input: content.input1, output: content.output1}
          templist.push(temp)
          temp = {input: content.input2, output: content.output2}
          templist.push(temp)
          temp = {input: content.input3, output: content.output3}
          templist.push(temp)
          temp = {input: content.input4, output: content.output4}
          templist.push(temp)
          temp = {input: content.input5, output: content.output5}
          templist.push(temp)
          temp = {input: content.input6, output: content.output6}
          templist.push(temp)
          temp = {input: content.input7, output: content.output7}
          templist.push(temp)
          temp = {input: content.input8, output: content.output8}
          templist.push(temp)
          temp = {input: content.input9, output: content.output9}
          templist.push(temp)
          temp = {input: content.input10, output: content.output10}
          templist.push(temp)
          this.in_output_list = templist
        })
      } else {
        ShowSelectedQuestion2(id.slice(0, 5)).then(res => {
          let content = res.data.data
          this.question_name = content.question_name
          this.description = content.question_description
          this.input_num = content.inputornot
          this.url = content.image1
          let temp = content.image1
          this.srcList.push(temp)
          temp = content.image2
          this.srcList.push(temp)
          let templist = []
          temp = {input: content.input1, output: content.output1}
          templist.push(temp)
          temp = {input: content.input2, output: content.output2}
          templist.push(temp)
          temp = {input: content.input3, output: content.output3}
          templist.push(temp)
          temp = {input: content.input4, output: content.output4}
          templist.push(temp)
          temp = {input: content.input5, output: content.output5}
          templist.push(temp)
          temp = {input: content.input6, output: content.output6}
          templist.push(temp)
          temp = {input: content.input7, output: content.output7}
          templist.push(temp)
          temp = {input: content.input8, output: content.output8}
          templist.push(temp)
          temp = {input: content.input9, output: content.output9}
          templist.push(temp)
          temp = {input: content.input10, output: content.output10}
          templist.push(temp)
          this.in_output_list = templist
        })
      }
    } else if (this.$store.state.add_question_mode === '4' || this.$store.state.add_question_mode === '6') {
      this.Add_To_Assignment_Mode = true
    }
    this.$store.commit('SET_IMAGELINK', '')
    this.$store.commit('SET_SOURCECODE', '')
  },
  computed: {
    newsourcecode () {
      this.changecode()
      return this.$store.state.sourcecode
    },
    newname () {
      this.checkname()
      return this.question_name
    },
    scrollerHeight: function () {
      console.log(document.body.scrollHeight)
      console.log(window.innerHeight - 46 - 50 - 10)
      if (document.body.scrollHeight < window.innerHeight) {
        return (window.innerHeight - 46 - 50 - 10) + 'px'
      } else {
        return (document.body.scrollHeight) + 'px'
      }
    }
  },
  methods: {
    questionsumbit () {
      console.log(this.question_name.trim())
      let output = []
      let input = []
      let temp = this.in_output_list.length
      input.push('null')
      output.push('null')
      for (let i = 0; i < temp; i++) {
        input.push(this.in_output_list[i].input)
        output.push(this.in_output_list[i].output)
      }
      for (let i = temp; i < 11; i++) {
        input.push('null')
        output.push('null')
      }
      let image1 = ''
      let image2 = ''
      if (this.Revise_Quesition_Mode) {
        image1 = this.srcList[1]
        image2 = this.srcList[2]
      } else {
        if (this.$store.state.imagelink.indexOf(',') >= 0) {
          image1 = this.$store.state.imagelink.slice(this.$store.state.imagelink.indexOf(',') + 1)
          image2 = this.$store.state.imagelink.slice(0, this.$store.state.imagelink.indexOf(','))
        } else {
          image1 = this.$store.state.imagelink
        }
      }
      let description = this.description ? this.description : ''
      let publicornot1 = this.publicbank ? 1 : 0
      if (!this.question_name) {
        this.$message.error('請輸入題目名稱')
        return
      }
      if (this.samename) {
        this.$message.error('不能與題目庫中已有的題目名稱相同')
        return
      }
      AddQuestionbank2({
        input: input,
        output: output,
        image1: image1,
        image2: image2,
        publicornot: publicornot1,
        teacher: this.$store.state.user.name,
        name: this.question_name,
        description: description,
        inputornot: temp
      })
        .then((resp) => {
          let code = resp.data.code
          if (this.$store.state.add_question_mode === '3' || this.$store.state.add_question_mode === '4' || this.$store.state.add_question_mode === '6' || this.$store.state.add_question_mode === '7') {
            getLatestQuestionID()
              .then((resp) => {
                let qid = resp.data.data.qid
                console.log(qid)
                qid = 'b' + this.padLeft(qid, 4)
                if (this.$store.state.selectedQuestion) {
                  qid = this.$store.state.selectedQuestion + ',' + qid
                }
                this.$store.commit('SET_SELECTEDQUESTION', qid)
                this.$store.commit('SET_CONTROLRELOAD', '1')
              })
          }
          if (code === 200) {
            this.$message({
              showClose: true,
              message: '成功新增題目',
              type: 'success'
            })
          } else {
            this.$message({
              showClose: true,
              message: '新增失敗',
              type: 'warning'
            })
          }
        })

      if (this.hassouececode) {
        AddSourceocde({
          question_name: this.question_name,
          code: this.sourcecode
        })
      }

      // get latest qid
      if (this.$store.state.add_question_mode === '3' || this.$store.state.add_question_mode === '4' || this.$store.state.add_question_mode === '6' || this.$store.state.add_question_mode === '7') {
        if (this.$store.state.add_question_mode === '4' || this.$store.state.add_question_mode === '3') {
          this.$router.replace({
            path: '/PublishAssignment'
          })
        } else {
          this.$router.replace({
            path: '/UpdateAssignment'
          })
        }
      }
    },
    add () {
      this.input_num += 1
      if (this.input_num > 10) {
        this.input_num -= 1
        this.$message({
          showClose: true,
          message: '測資數目不可大於10',
          type: 'warning'
        })
      } else {
        console.log('add ' + document.body.scrollHeight)
        this.height = (document.body.scrollHeight - 101) + 'px'
        this.in_output_list.push(JSON.parse(JSON.stringify(this.object)))
      }
    },
    sub () {
      this.input_num -= 1
      if (this.input_num < 0) {
        this.input_num += 1
        this.$message({
          showClose: true,
          message: '測資數目不可小於0，如果不要測資，請將有無測資取消勾選',
          type: 'warning'
        })
      } else {
        console.log('sub ' + document.body.scrollHeight)
        this.height = (document.body.scrollHeight - 101) + 'px'
        this.in_output_list.pop()
      }
    },
    onUploadChange (file, fileList) {
      const isIMAGE =
        file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' // 判別格式
      const isLt1M = file.size / 1024 / 1024 < 500000// 判別大小

      if (!isIMAGE) {
        this.$message.error('只能上傳jpg,png格式')
        fileList.pop()
        return false
      }
      if (!isLt1M) {
        this.$message.error('上傳圖片大小不能大於5MB')
        fileList.pop()
        return false
      }
      this.imageList.push(file)
      this.imageupload(file.url, 'png')
    },
    onfileUploadChange (file, fileList) {
      // 尚未完成
      this.fileList.push(file)
      let reader = new FileReader()
      reader.onload = function () {
        if (reader.result) {
          this.$store.commit('SET_SOURCECODE', reader.result)
        }
      }
      reader.readAsText(file.raw, 'gb2312')
      this.hassouececode = true
    },
    onUploadRemove (file) {
      if (this.imageList[0].url === file.url) {
        this.imageList.shift()
        this.$store.commit('SET_IMAGELINK', this.$store.state.imagelink.slice(this.$store.state.imagelink.indexOf(',') + 1))
      } else {
        this.imageList.pop()
        this.$store.commit('SET_IMAGELINK', this.$store.state.imagelink.slice(0, this.$store.state.imagelink.indexOf(',')))
      }
    },
    onfileUploadRemove (file) {
      this.fileList.pop()
      this.hassouececode = false
    },
    imageupload (url, ext) {
      var canvas = document.createElement('canvas')
      var ctx = canvas.getContext('2d')
      var img = new Image()
      img.crossOrigin = 'Anonymous'
      img.src = url
      img.onload = function () {
        canvas.height = img.height
        canvas.width = img.width
        ctx.drawImage(img, 0, 0)
        var dataURL = canvas.toDataURL('image/' + ext)
        canvas = null
        // upload to imgur
        var myHeaders = new Headers()
        myHeaders.append('Authorization', 'Client-ID de2ed01ef5866f3')
        var formdata = new FormData()
        formdata.append('image', dataURL.replace('data:image/png;base64,', ''))

        var requestOptions = {
          method: 'POST',
          headers: myHeaders,
          body: formdata,
          redirect: 'follow'
        }

        fetch('https://api.imgur.com/3/image', requestOptions)
          .then(response => response.text())
          .then(result => {
            if (this.$store.state.imagelink) {
              this.$store.commit('SET_IMAGELINK', this.$store.state.imagelink + ',' + JSON.parse(result).data.link)
            } else {
              this.$store.commit('SET_IMAGELINK', JSON.parse(result).data.link)
            }
          })
          .catch(error => console.log('error', error))
      }
    },
    handleExceed () {
      this.$message({
        showClose: true,
        message: '最多上傳2個圖檔，請勿超過!',
        type: 'warning'
      })
    },
    handlefileExceed () {
      this.$message({
        showClose: true,
        message: '最多上傳1個程式碼檔案，請勿超過!',
        type: 'warning'
      })
    },
    test () {
      alert('test')
    },
    changecode () {
      this.sourcecode = this.$store.state.sourcecode
    },
    checkname () {
      this.samename = false
      ShowQuestion2().then(res => {
        var temp = JSON.parse(JSON.stringify(res.data.data.Questions))
        for (let i = 0; i < temp.length; i++) {
          if (this.question_name.trim() === temp[i].question_name) {
            this.$message({
              showClose: true,
              message: '不能與題目庫中已有的題目名稱相同',
              type: 'error'
            })
            this.samename = true
            return true
          }
        }
      })
    },
    padLeft (str, lenght) {
      if (str.length >= lenght) {
        return str
      } else {
        return this.padLeft('0' + str, lenght)
      }
    }
  }
}

</script>

<style scoped>
#temp {
  width: 40%;
  margin-right: 0%;
  margin-left: 10%;
  line-height:80px;
  text-align:center;
  float:left;
}
#sitebody{
  background-color: #EDEDED;
  width:100%;
  margin:0 auto;
  font-size:13px;
}
#header{
  width: 70%;
  text-align:center;
  line-height:50px;
  margin-left:15%;
  padding: -3px;
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
  width: 80%;
  margin-top: 20px;
  margin-left:10%;
  text-align:center;
}
#footer{
  clear:both;
  margin-right: 10%;
  text-align:right;
  line-height:80px;
  float:right;
}
#footer-left{
  width: 40%;
  margin-right: 0%;
  margin-left: 10%;
  text-align:left;
  line-height:80px;
  float:left;
}
#footer-right{
  width: 40%;
  margin-right: 10%;
  margin-left: 0%;
  text-align:center;
  line-height:80px;
  float:right;
}
</style>
