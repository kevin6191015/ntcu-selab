<template>
  <body>
    <el-backtop></el-backtop>
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
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
            label="複製並修改題目"
          >
            <template slot-scope="scope">
              <el-button @click="revise1(scope.row)" type="text" size="small">選擇該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目預覽"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion1(scope.row)" type="text" size="small">查看該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目程式碼"
          >
            <template slot-scope="scope">
              <el-button @click="SeeCode1(scope.row)" type="text" size="small">查看程式碼</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="公共題庫" name="third">
        <el-table
          ref="multipleTable3"
          :data="content3"
          stripe
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange3"
        >
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column type="index" label="序號" ></el-table-column>
          <el-table-column prop="question_name" label="題目名稱"></el-table-column>
          <el-table-column prop="teacher" label="出題老師"></el-table-column>
          <el-table-column
            fixed="right"
            label="複製並修改題目"
          >
            <template slot-scope="scope">
              <el-button @click="revise2(scope.row)" type="text" size="small">選擇該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目預覽"
          >
            <template slot-scope="scope">
              <el-button @click="Seequestion2(scope.row)" type="text" size="small">查看該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目程式碼"
          >
            <template slot-scope="scope">
              <el-button @click="SeeCode2(scope.row)" type="text" size="small">查看程式碼</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="私人題庫" name="second">
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
            label="複製並修改題目"
          >
            <template slot-scope="scope">
              <el-button @click="revise2(scope.row)" type="text" size="small">選擇該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目預覽"
            >
            <template slot-scope="scope">
              <el-button @click="Seequestion2(scope.row)" type="text" size="small">查看該題</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="題目程式碼"
          >
            <template slot-scope="scope">
              <el-button @click="SeeCode2(scope.row)" type="text" size="small">查看程式碼</el-button>
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
import {ShowQuestion1, ShowQuestion2byTeacher, ShowPublicQuestion} from '@/api/question'
export default {
  data () {
    return {
      classid: '',
      selected: {},
      content1: [],
      content2: [],
      content3: [],
      multipleSelection1: [],
      multipleSelection2: [],
      multipleSelection3: [],
      activeName: 'first',
      temp: [],
      check1: true,
      check2: true
    }
  },
  created () {
    if (this.$store.state.add_question_mode === '3' || this.$store.state.add_question_mode === '7') {
    }
    ShowQuestion1().then(res => {
      this.content1 = JSON.parse(JSON.stringify(res.data.data.Questions))
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
    })
  },
  updated () {
    this.temp = this.$store.state.selectedQuestion
    if (this.temp) {
      this.toggleSelection1(this.temp)
      this.toggleSelection2(this.temp)
      this.toggleSelection3(this.temp)
    }
  },
  methods: {
    handleSelectionChange1 (theval) {
      this.multipleSelection1 = theval
    },
    handleSelectionChange2 (theval) {
      this.multipleSelection2 = theval
    },
    handleSelectionChange3 (theval) {
      this.multipleSelection3 = theval
    },
    handleClick (tab) {
      if (tab.name === 'second') {
        if (this.content2.length === 0) {
          ShowQuestion2byTeacher(this.$store.state.user.name).then(res => {
            this.content2 = JSON.parse(JSON.stringify(res.data.data.Questions))
          }).catch(error => {
            this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
              confirmButtonText: '確定'
            })
          })
        }
      } else if (tab.name === 'third') {
        if (this.content3.length === 0) {
          ShowPublicQuestion().then(res => {
            this.content3 = JSON.parse(JSON.stringify(res.data.data.Questions))
          }).catch(error => {
            this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
              confirmButtonText: '確定'
            })
          })
        }
      }
    },
    select () {
      this.$store.commit('REMOVE_SELECTEDQUESTION')
      this.$store.commit('SET_CONTROLRELOAD', '1')
      let selectedQ = []
      let num1 = 0
      let num2 = 0
      for (let i = 0; i < this.multipleSelection1.length; i++) {
        selectedQ[i] = this.multipleSelection1[i].question_id
        num1++
      }
      if (this.multipleSelection2) {
        for (let i = 0; i < this.multipleSelection2.length; i++) {
          selectedQ[(i + num1)] = this.multipleSelection2[i].id
          num2++
        }
      }
      if (this.multipleSelection3) {
        for (let i = 0; i < this.multipleSelection3.length; i++) {
          selectedQ[(i + num2 + num1)] = this.multipleSelection3[i].id
        }
      }
      this.$store.commit('SET_SELECTEDQUESTION', selectedQ)
      if (this.$store.state.add_question_mode === '5') {
        this.$router.replace({
          path: '/UpdateAssignment'
        })
      } else {
        this.$router.replace({
          path: '/PublishAssignment'
        })
      }
    },
    Seequestion1 (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    Seequestion2 (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowQuestion'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    SeeCode1 (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.question_id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowSourcecode'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    SeeCode2 (row) {
      this.$store.commit('SET_QUESTION_TO_SHOW', row.id + ',' + row.question_name)
      let { href } = this.$router.resolve({
        name: 'ShowSourcecode'
      })
      window.open(href, '_blank', 'toolbar=yes, width=1000, height=700')
      this.$store.commit('REMOVE_QUESTION_TO_SHOW')
    },
    revise1 (row) {
      if (this.$store.state.add_question_mode === '1') {
        this.$store.commit('SET_ADD_QUESTION_MODE', '3')
      } else if (this.$store.state.add_question_mode === '5') {
        this.$store.commit('SET_ADD_QUESTION_MODE', '7')
      }
      this.$store.commit('SET_QUESTION_TO_SHOW', row.question_id)
      this.$router.replace({
        path: '/AddQuestion'
      })
    },
    revise2 (row) {
      if (this.$store.state.add_question_mode === '1') {
        this.$store.commit('SET_ADD_QUESTION_MODE', '3')
      } else if (this.$store.state.add_question_mode === '5') {
        this.$store.commit('SET_ADD_QUESTION_MODE', '7')
      }
      this.$store.commit('SET_QUESTION_TO_SHOW', row.id)
      this.$router.replace({
        path: '/AddQuestion'
      })
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
    },
    toggleSelection3 (selectedQ) {
      for (let i = 0; i < selectedQ.length; i = i + 6) {
        if (selectedQ[i].charAt(0) === 'b') {
          for (let j = 0; j < this.content3.length; j++) {
            if (this.content3[j].id === selectedQ.slice(i, i + 5)) {
              this.$refs.multipleTable3.toggleRowSelection(this.content3[j])
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
