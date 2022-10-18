<template>
  <el-container class="container2">
    <el-main>
      <div style="margin:10px"></div>
      <el-row>
        <el-table
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
    </el-main>
  </el-container>
</template>

<script>
import { getAllAssignments } from '@/api/assignment'
export default {
  name: 'ShowStudentAssignment',
  data () {
    return {
      debuted_list: []
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
      }
    })
  },
  methods: {
    seleted_class (seleted) {
      seleted.release_time = seleted.release_time.substring(0, 4) + seleted.release_time.substring(5, 7) + seleted.release_time.substring(8, 10)
      seleted.deadline = seleted.deadline.substring(0, 4) + seleted.deadline.substring(5, 7) + seleted.deadline.substring(8, 10)
      this.$store.commit('SET_ASSIGNMENT', seleted)
      this.$router.replace({
        path: '/ShowStudentHomework'
      })
    }
  }
}
</script>

<style>
.container2 {
  background-color: rgb(228, 228, 228);
  height: 635px;
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
