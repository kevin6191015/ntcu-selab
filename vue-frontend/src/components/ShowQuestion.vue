<template>
<body>
  <el-table
  ref="multipleTable"
  :data="content"
  tooltip-effect="dark"
  style="width: 100%"
  @current-change="handleSelectionChange"
  >
  <el-table-column
      label="操作"
      width="55">
    <template slot-scope="scope">
      <el-checkbox v-model="scope.row.checked"></el-checkbox>
    </template>
  </el-table-column>
    <el-table-column type="index" label="序號" ></el-table-column>
    <el-table-column prop="question_name" label="題目名稱"></el-table-column>
  </el-table>
  <div id="footer-left">
    <el-button  @click='select'>確認</el-button>
  </div>
  <h3>variable:</h3>
 {{this.selected}}
</body>
</template>
<script>
import {ShowQuestion1} from '../api/question'
export default {
  name: 'GetStudent',
  data () {
    return {
      classid: '',
      selected: {},
      content: []
    }
  },
  created () {
    ShowQuestion1().then(res => {
      this.content = JSON.parse(JSON.stringify(res.data.data.Questions))
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
    })
  },
  methods: {
    handleSelectionChange (row) {
      this.selected = row
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

  #footer-left{
  clear:both;
  margin-right: 5%;
  margin-left: 5%;
  text-align:center;
  line-height:80px;
  float:right;
}
  .layout-footer-center{
    text-align: center;
  }
</style>
