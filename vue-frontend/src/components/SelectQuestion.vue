<template>
<body>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="系統題庫" name="first">
      <el-table
        ref="multipleTable1"
        :data="content1"
        stripe
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
          label="題目預覽"
        >
          <template slot-scope="scope">
            <el-button @click="Seequestion1(scope.row)" type="text" size="small">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="題目程式碼"
        >
          <template slot-scope="scope">
            <el-button @click="SeeCode1(scope.row)" type="text" size="small">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="第二題庫" name="second">
      <el-table
        ref="multipleTable2"
        :data="content2"
        stripe
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
        <el-table-column
          fixed="right"
          label="題目程式碼"
        >
          <template slot-scope="scope">
            <el-button @click="SeeCode2(scope.row)" type="text" size="small">查看</el-button>
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
  data () {
    return {
      classid: '',
      selected: {},
      content1: [],
      content2: [],
      multipleSelection1: [],
      multipleSelection2: [],
      activeName: 'first',
      temp: [],
      check1: true,
      check2: true
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
  updated () {
    this.temp = store.state.selectedQuestion
    if (this.temp) {
      this.toggleSelection1(this.temp)
      this.toggleSelection2(this.temp)
    }
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
      store.commit('REMOVE_SELECTEDQUESTION')
      let selectedQ = []
      let num
      for (let i = 0; i < this.multipleSelection1.length; i++) {
        selectedQ[i] = this.multipleSelection1[i].question_id
        num = i
      }
      for (let i = 0; i < this.multipleSelection2.length; i++) {
        selectedQ[((num > 0) ? (i + num + 1) : (i))] = this.multipleSelection2[i].id
      }
      store.commit('SET_SELECTEDQUESTION', selectedQ)
      this.$router.replace({
        path: '/PublishAssignment'
      })
    },
    Seequestion1 (row) {
      store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    Seequestion2 (row) {
      store.commit('SET_QUESTION_TO_SHOW', row.id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    SeeCode1 (row) {
      store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowSourcecode'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    SeeCode2 (row) {
      store.commit('SET_QUESTION_TO_SHOW', row.id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowSourcecode'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    toggleSelection1 (selectedQ) {
      for (let i = 0; i < selectedQ.length; i = i + 6) {
        if (selectedQ[i].charAt(0) === 'a') {
          for (let j = 0; j < this.content1.length; j++) {
            if (this.content1[j].question_id === selectedQ.slice(i, i + 5)) {
              this.$refs.multipleTable1.toggleRowSelection(this.content1[j])
            }
          }
        } else {
          break
        }
      }
    },
    toggleSelection2 (selectedQ) {
      for (let i = 0; i < selectedQ.length; i = i + 6) {
        if (selectedQ[i].charAt(0) === 'b') {
          for (let j = 0; j < this.content2.length; j++) {
            if (this.content2[j].id === selectedQ.slice(i, i + 5)) {
              this.$refs.multipleTable2.toggleRowSelection(this.content2[j])
            }
          }
        } else {
        }
      }
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
