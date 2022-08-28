<template>
  <el-container>
    <el-main class="container outer">
      <el-row :gutter="100" style="padding: 5%;">
        <el-col :span="12" class="el-col1"><div class="grid-content">未加入帳號</div></el-col>
        <el-col :span="12" class="el-col1"><div class="grid-content">已加入帳號</div></el-col>
      </el-row>
      <el-row :gutter="100">
        <el-col :span="11">
          <div class="grid-content2">
            <el-table
              ref="multipleTable"
              :data="not_class_student"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="handleSelectionChange">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="student_name"
                width="120">
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :span="11">
          <div class="grid-content2">
            <el-table
              ref="multipleTable"
              :data="class_student"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="handleSelectionChange">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                label="student_name"
                width="120">
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getUser } from '@/api/user'
import store from '../store'
export default {
  name: 'CourseAccount',
  data () {
    return {
      class_student: [],
      not_class_student: [],
      multipleSelection: [],
      all_user: []
    }
  },
  created () {
    getUser().then(res => {
      this.class_student = []
      this.not_class_student = []
      for (let i = 0; i < res.data.data.Users.length; i++) {
        for (let j = 0; j < res.data.data.Users[i].CLASSES.split(',').length; j++) {
          if (res.data.data.Users[i].CLASSES.split(',')[j] === store.state.class_id) {
            this.class_student.push(res.data.data.Users[i])
          }
        }
      }
      for (let i = 0; i < res.data.data.Users.length; i++) {
        let check = true
        for (let j = 0; j < this.class_student.length; j++) {
          if (res.data.data.Users[i].role !== 'student' || res.data.data.Users[i].name === this.class_student[j].name) {
            check = false
          }
        }
        if (check) {
          this.not_class_student.push(res.data.data.Users[i])
        }
      }
    })
  },
  methods: {
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    }
  }
}
</script>

<style>
.container {
  width: 800px;
  margin: 0 auto;
}

.d-flex {
  display: flex;
  flex-wrap: wrap;
}

.el-col1 {
  border-radius: 10px;
  min-height: 36px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
    font-size: 30px;
    background-color: orange;
    padding-left: 36%
  }

  .grid-content2 {
    min-height: 36px;
    font-size: 30px;
    padding-left: 25%
  }

.outer {
  background-color: rgba(111, 122, 144, 0.555);
  height: 700px;
  padding: 15px;
}

</style>
