<template>
  <div>
    <div id="sitebody">
      <div id="header">
        <div id="space-top"></div>
        <div id="qname-green">
          <h3>題目名稱:{{content.question_name}}</h3>
        </div>
      </div>
      <div id="content">
        <h3>題目敘述:</h3>
        <div v-show="hasimage1">
          <el-image
            :src= "content.image1"
            fit = "fit"></el-image>
        </div>
        <div v-show="hasimage2">
          <el-image
            :src= "content.image2"
            fit = "fit"></el-image>
        </div>
        {{content.question_description}}
      </div>
      <div id="footer">
        <el-button  @click='goback'>關閉視窗</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {ShowSelectedQuestion1, ShowSelectedQuestion2} from '@/api/question'
export default {
  data () {
    return {
      content: [],
      hasimage1: false,
      hasimage2: false
    }
  },
  created () {
    let id = this.$store.state.Question_To_Show
    if (id.charAt(0) === 'a') {
      ShowSelectedQuestion1(id.slice(0, 5)).then(res => {
        this.content = res.data.data
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    } else {
      ShowSelectedQuestion2(id.slice(0, 5)).then(res => {
        this.content = res.data.data
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
      })
    }
  },
  beforeUpdate () {
    this.hasimage1 = !((this.content.image1.length === 0) || (this.content.image1 === 'null'))
    this.hasimage2 = !((this.content.image2.length === 0) || (this.content.image2 === 'null'))
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
  border: 3px solid rgba(0, 0, 0, 0.397);
  background-color: rgba(228, 228, 228, 0.651);
  border-radius: 12px;
}
#content{
  border: 3px solid rgba(0, 0, 0, 0.397);
  background-color: rgb(228, 228, 228);
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
