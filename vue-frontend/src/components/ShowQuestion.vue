<template>
<body>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="預設題庫" name="first">
      <el-table
        ref="multipleTable"
        :data="content1"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange1"
      >
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column type="index" label="序號" ></el-table-column>
        <el-table-column prop="question_name" label="題目名稱"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
        >
          <template slot-scope="scope">
            <el-button @click="Seequestion1(scope.row)" type="text" size="small">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="用户管理" name="second">
      <el-table
        ref="multipleTable"
        :data="content2"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange2"
        >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column type="index" label="序號" ></el-table-column>
        <el-table-column prop="question_name" label="題目名稱"></el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          >
          <template slot-scope="scope">
            <el-button @click="Seequestion2(scope.row)" type="text" size="small">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
  </el-tabs>
  <div id="footer-left">
    <el-button  @click='select'>確認</el-button>
  </div>
</body>
</template>
<script>
import {ShowQuestion1, ShowQuestion2} from '../api/question'
import store from '../store'
export default {
  name: 'GetStudent',
  data () {
    return {
      classid: '',
      selected: {},
      content1: [],
      content2: [],
      multipleSelection1: [],
      multipleSelection2: [],
      activeName: 'first'

    }
  },
  created () {
    ShowQuestion1().then(res => {
      this.content1 = JSON.parse(JSON.stringify(res.data.data.Questions))
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
    })
  },
  methods: {
    handleSelectionChange1 (theval) {
      this.multipleSelection1 = theval
    },
    handleSelectionChange2 (theval) {
      this.multipleSelection2 = theval
    },
    handleClick (tab) {
      if (tab.name === 'second') {
        ShowQuestion2().then(res => {
          this.content2 = JSON.parse(JSON.stringify(res.data.data.Questions))
        }).catch(error => {
          this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
            confirmButtonText: '確定'
          })
        })
      }
    },
    select () {
      let selectedQ = []
      let num
      for (let i = 0; i < this.multipleSelection1.length; i++) {
        selectedQ[i] = this.multipleSelection1[i].question_id
        num = i
      }
      for (let i = 0; i < this.multipleSelection2.length; i++) {
        selectedQ[(i + num + 1)] = this.multipleSelection2[i].id
      }
      store.commit('SET_SELECTEDQUESTION', selectedQ)
      alert(selectedQ)
    },
    Seequestion1 (row) {
      alert(row.question_id)
    },
    Seequestion2 (row) {
      alert(row.id)
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
