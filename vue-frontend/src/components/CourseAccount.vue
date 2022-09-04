<template>
  <el-container>
    <el-main class="container1 outer1">
      <el-row >
        <div style="margin: 5%;"></div>
        <el-col :span="8" class="el-col1"><div class="grid-content1">未加入帳號</div></el-col>
        <el-col :span="8" class="el-col1"><div class="grid-content1">已加入帳號</div></el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <div class="grid-content2">
            <el-table
              ref="multipleTable"
              :data="not_class_student"
              tooltip-effect="dark"
              style="min-height: 400px;"
              @selection-change="not_in_Course">
              <el-table-column
                type="selection"
                width="150">
              </el-table-column>
              <el-table-column
                label="student_name"
                width="240">
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :span="11">
          <div>
            <el-table
              ref="multipleTable"
              :data="class_student"
              tooltip-effect="dark"
              style="min-height: 36px;margin-left: 29.5%;width: 72.5%; min-height: 400px;"
              @selection-change="in_Course">
              <el-table-column
                type="selection"
                width="150">
              </el-table-column>
              <el-table-column
                label="student_name"
                width="240">
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
      <div style="margin: 20px;"></div>
      <el-row :gutter="10">
        <el-col :span="5">
          <div>
            <el-button type="warning" style="margin: 20px; font-size: 20px; margin-left: 65%; color: black;">新增</el-button>
          </div>
        </el-col>
        <el-col :span="5">
          <div>
            <el-button type="warning" style="margin: 20px; font-size: 20px; margin-left:90%; color: black;">匯入</el-button>
          </div>
        </el-col>
        <el-col :span="5">
          <div>
            <el-button type="warning" style="margin: 20px; font-size: 20px; margin-left: 85%; color: black;">刪除</el-button>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getUser } from '@/api/user'
import store from '../store'
import { addStudent, deleteStudent } from '@/api/student'
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
    not_in_Course (val) {
      this.not_class_student = val
      console.log(this.not_class_student)
      for (let i = 0; i < this.not_class_student.length; i++) {
        addStudent({
          class_id: store.state.class_id,
          user_id: this.not_class_student[i].id
        }).then(res => {
          this.$message({
            showClose: true,
            message: res.data.data.message,
            type: 'success'
          })
        }).catch(error => {
          this.$message({
            showClose: true,
            message: error,
            type: 'warning'
          })
        })
      }
    },
    in_Course (val) {
      this.class_student = val
      console.log(this.class_student)
      for (let i = 0; i < this.class_student.length; i++) {
        deleteStudent({
          class_id: store.state.class_id,
          user_id: this.class_student[i].id
        }).then(res => {
          this.$message({
            showClose: true,
            message: res.data.data.message,
            type: 'success'
          })
        }).catch(error => {
          this.$message({
            showClose: true,
            message: error,
            type: 'warning'
          })
        })
      }
    }
  }
}
</script>

<style>
.container1 {
  width: 800px;
  margin: 0 auto;
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

.outer1 {
  background-color: rgba(111, 122, 144, 0.555);
  height: 700px;
}

</style>
