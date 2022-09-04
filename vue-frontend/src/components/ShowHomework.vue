<template>
  <el-container class="container2">
    <el-main>
      <el-row>
        <el-col style="width: 5%; float:right; background-color:brown; font-size: 20px;">已發布</el-col>
      </el-row>
      <el-row>
        <el-table
          :data="tableData"
          stripe
          style="width: 100%">
          <el-table-column
            prop="question_name"
            label="題目名稱"
            width="180">
          </el-table-column>
          <el-table-column
            prop="all_number"
            label="作答人數/總人數"
            width="180">
          </el-table-column>
          <el-table-column
            prop="number"
            label="答對人數">
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
import store from '../store'
export default {
  name: 'ShowHomework',
  data () {
    return {
      tableData: [{
        question_name: '123',
        all_number: '123/123',
        number: '123',
        release_time: '2020/2/22',
        deadline: '2020/3/3'
      }]
    }
  },
  created () {
    getAllAssignments({
      cid: store.state.class_id
    }).then(res => {
      this.tableData = res.data.data.Assignments
    })
  }
}
</script>

<style>
.container2 {
  width: 1250px;
  margin: 0 auto;
  background-color: rgba(111, 122, 144, 0.555);
  height: 650px;
}

.d-flex1 {
  display: flex;
  flex-wrap: wrap;
}

.el-col1 {
  border-radius: 10px;
  min-height: 36px;
  margin-left: 13%;
}

.grid-content1 {
  min-height: 36px;
  font-size: 30px;
  background-color: orange;
  padding-left: 30%;
}

.grid-content2 {
  min-height: 36px;
  font-size: 30px;
  margin-left: 28.4%;
  width: 72.5%;
}

.grid-content3 {
  font-size: 13px;
  padding-left: 50%
}

</style>
