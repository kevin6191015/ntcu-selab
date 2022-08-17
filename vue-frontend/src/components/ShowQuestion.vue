<template>
<body>
  <el-table :data="content" border stripe>
      <el-table-column type="index" label="序號"></el-table-column>
  </el-table>
</body>
</template>
<script>
import {getStudent} from '../api/student'
import {ShowQuestion1} from '../api/question'
export default {
  name: 'GetStudent',
  data () {
    return {
      classid: '',
      content: []
    }
  },
  created () {
    alert('good')
    ShowQuestion1()
  },
  methods: {
    getStudentList () {
      getStudent({class_id: this.classid}).then(res => {
        this.content = res.data.Students
      }).catch(error => {
        this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
          confirmButtonText: '確定'
        })
        var path = this.$route.query.redirect
        this.$router.replace({
          path: path === '/' || path === undefined ? '/user' : path})
      })
    }
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    height: 100%;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
    font-weight: bold;
    text-align: center;
    color: #49ffcc;
  }
  .layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
  }
  .layout-footer-center{
    text-align: center;
  }
</style>
