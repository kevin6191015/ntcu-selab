<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-button class="button1" @click="change1">已公布</el-button>
        <el-button class="button1" @click="change2">未公布</el-button>
      </el-row>
      <div style="margin:10px"></div>
      <el-row>
        <el-table
          v-if="debuted"
          :data="debuted_list"
          stripe
          highlight-current-row
          @current-change="seleted_class"
          style="width: 100%">
          <el-table-column
            prop="assignment_name"
            label="題目名稱"
            width="250">
          </el-table-column>
          <el-table-column
            prop="release_time"
            label="出題日期">
          </el-table-column>
          <el-table-column
            prop="deadline"
            label="截止日期">
          </el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-table
          v-if="not_debuted"
          :data="not_debuted_list"
          stripe
          highlight-current-row
          @current-change="seleted_class"
          style="width: 100%">
          <el-table-column
            prop="assignment_name"
            label="題目名稱"
            width="250">
          </el-table-column>
          <el-table-column
            prop="release_time"
            label="出題日期">
          </el-table-column>
          <el-table-column
            prop="deadline"
            label="截止日期">
          </el-table-column>
        </el-table>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getAllAssignments } from '../api/assignment'
export default {
  name: 'ShowAssignment',
  data () {
    return {
      debuted_list: [],
      not_debuted_list: [],
      debuted: true,
      not_debuted: false
    }
  },
  created () {
    getAllAssignments({
      cid: this.$store.state.class_id
    }).then(res => {
      let tmp = res.data.data.Assignments
      for (let i = 0; i < tmp.length; i++) {
        tmp[i].release_time = tmp[i].release_time.substring(0, 4) + '/' + tmp[i].release_time.substring(4, 6) + '/' + tmp[i].release_time.substring(6, 8)
        tmp[i].deadline = tmp[i].deadline.substring(0, 4) + '/' + tmp[i].deadline.substring(4, 6) + '/' + tmp[i].deadline.substring(6, 8)
        let time = parseInt(tmp[i].release_time.substring(0, 4)) * 12 + parseInt(tmp[i].release_time.substring(5, 7)) * 31 + parseInt(tmp[i].release_time.substring(8, 10))
        var Today = new Date()
        let now = parseInt(Today.getFullYear()) * 12 + parseInt(Today.getMonth() + 1) * 31 + parseInt(Today.getDate())
        if (now >= time) {
          if (this.debuted_list.length > 0) {
            let check = true
            for (let j = 0; j < this.debuted_list.length; j++) {
              if (this.debuted_list[j].assignment_name === tmp[i].assignment_name) {
                check = false
              }
            }
            if (check) {
              this.debuted_list.push(tmp[i])
            }
          } else {
            this.debuted_list.push(tmp[i])
          }
        } else {
          if (this.not_debuted_list.length > 0) {
            let check = true
            for (let j = 0; j < this.not_debuted_list.length; j++) {
              if (this.not_debuted_list[j].assignment_name === tmp[i].assignment_name) {
                check = false
              }
            }
            if (check) {
              this.not_debuted_list.push(tmp[i])
            }
          } else {
            this.not_debuted_list.push(tmp[i])
          }
        }
      }
    })
  },
  methods: {
    seleted_class (seleted) {
      seleted.release_time = seleted.release_time.substring(0, 4) + seleted.release_time.substring(5, 7) + seleted.release_time.substring(8, 10)
      seleted.deadline = seleted.deadline.substring(0, 4) + seleted.deadline.substring(5, 7) + seleted.deadline.substring(8, 10)
      this.$store.commit('SET_ASSIGNMENT', seleted)
      this.$router.replace({
        path: '/ShowHomeWork'})
    },
    change1 () {
      if (!this.debuted) {
        this.debuted = true
        this.not_debuted = false
      }
    },
    change2 () {
      if (!this.not_debuted) {
        this.not_debuted = true
        this.debuted = false
      }
    }
  }
}
</script>

<style>
.container2 {
  background-color: rgb(228, 228, 228);
  height: 699px;
}
.button1 {
  width: 6%;
  float:right;
  background-color:rgb(77, 42, 165);
  font-size: 20px;
  padding: 5px;
  color: aliceblue;
  margin: 5px;
}
</style>
