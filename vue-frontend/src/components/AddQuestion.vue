<template>
  <div >
    <div id="sitebody">
      <div id="header">
        <h3>新增題目</h3>
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
      </div>
      <div id = "content">
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
            <el-input clearble type="textarea" v-model="scope.row.input" placeholder="清輸入input"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          property="output"
          label="輸出"
          fixed="right">
          <template slot-scope="scope">
            <el-input clearble type="textarea" v-model="scope.row.output" placeholder="清輸入output"></el-input>
          </template>
        </el-table-column>
        </el-table>
        <el-button sytle="width:50%;height:50px;backbround:#fff;color:#000" icon="el-icon-plus" @click="add" type="primary">增加輸入、輸出</el-button>
        <el-button sytle="width:50%;height:50px;backbround:#fff;color:#000" icon="el-icon-minus" @click="sub" type="primary">減少輸入、輸出</el-button>
      </div>
      <div id = "footer-left">
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
        <el-button type="warning" icon="el-icon-upload">圖片上傳</el-button>
        </el-upload>
      </div>
      <div id ="footer-right">
        <el-upload
          action
          :on-change="onfileUploadChange"
          :before-remove="onfileUploadRemove"
          :on-exceed="handlefileExceed"
          :limit="1"
          :auto-upload="false"
          :show-file-list="true"
          :file-list="fileList"
        >
          <el-button type="warning" icon="el-icon-upload">程式碼上傳</el-button>
        </el-upload>
        <el-input clearble type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="sourcecode" placeholder="程式碼內容"></el-input>
      </div>
    <div id="footer">
      <el-button type="success" icon="el-icon-s-check" @click='questionsumbit'>確認</el-button>
    </div>
    <div v-show="notshow">
      code
      {{newsourcecode}}
    </div>
  </div>
  </div>
</template>

<script scoped>
import { AddQuestionbank2, AddSourceocde } from '../api/question'
import store from '../store'
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
      hassouececode: false
    }
  },
  created () {
    store.commit('SET_IMAGELINK', '')
    store.commit('SET_SOURCECODE', '')
  },
  computed: {
    newsourcecode () {
      this.changecode()
      return this.$store.state.sourcecode
    }
  },
  methods: {
    questionsumbit () {
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
      if (store.state.imagelink.indexOf(',') >= 0) {
        image1 = this.$store.state.imagelink.slice(store.state.imagelink.indexOf(',') + 1)
        image2 = this.$store.state.imagelink.slice(0, store.state.imagelink.indexOf(','))
      } else {
        image1 = this.$store.state.imagelink
      }

      let description = this.description ? this.description : ''

      if (!this.question_name) {
        this.$message.error('請輸入題目名稱')
        return
      }
      AddQuestionbank2({
        input: input,
        output: output,
        image1: image1,
        image2: image2,
        classid: store.state.class_id,
        teacher: store.state.user.name,
        name: this.question_name,
        description: description,
        inputornot: temp
      })
        .then((resp) => {
          let code = resp.data.code
          if (code === 200) {
            this.$message({
              showClose: true,
              message: '成功新增題目',
              type: 'warning'
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
          store.commit('SET_SOURCECODE', reader.result)
        }
      }
      reader.readAsText(file.raw, 'gb2312')
      this.hassouececode = true
    },
    onUploadRemove (file) {
      if (this.imageList[0].url === file.url) {
        this.imageList.shift()
        store.commit('SET_IMAGELINK', store.state.imagelink.slice(store.state.imagelink.indexOf(',') + 1))
      } else {
        this.imageList.pop()
        store.commit('SET_IMAGELINK', store.state.imagelink.slice(0, store.state.imagelink.indexOf(',')))
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
            if (store.state.imagelink) {
              store.commit('SET_IMAGELINK', store.state.imagelink + ',' + JSON.parse(result).data.link)
            } else {
              store.commit('SET_IMAGELINK', JSON.parse(result).data.link)
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
      this.sourcecode = store.state.sourcecode
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
  text-align:center;
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
