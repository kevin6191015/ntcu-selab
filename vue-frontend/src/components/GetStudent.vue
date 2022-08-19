<template>
<body>
  <el-input  placeholder="請輸入內容" v-model="classid" clearable>
    <el-button slot="append" icon="el-icon-search" @click='getStudentList'></el-button>
  </el-input>
  <el-table :data="content" border stripe>
      <el-table-column type="index" label="序號"></el-table-column>
      <el-table-column prop="student_name" label="名字"></el-table-column>
      <el-table-column prop="student_id" label="學號"></el-table-column>
  </el-table>
</body>
</template>
<script>
import {getStudent} from '../api/student'
export default {
  name: 'GetStudent',
  data () {
    return {
      classid: '',
      content: []
    }
  },
  methods: {
    getStudentList () {
      getStudent({class_id: this.classid}).then(res => {
        this.content = res.data.data.Students
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
