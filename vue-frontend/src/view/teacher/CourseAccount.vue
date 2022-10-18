<template>
  <el-container class="iner1">
    <!-- <div style="background-color: rgba(0,0,0,0.05); width:100%;margin:20px"> -->
      <el-main style="margin: 40px;">
      <el-row :gutter="30">
        <el-col :span="8" class="el-col1"><div align="center" class="grid-content1">未加入帳號</div></el-col>
        <el-col :offset="3" :span="8" class="el-col1"><div align="center" class="grid-content1">已加入帳號</div></el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col align="center" :span="8">
          <div class="grid-content2">
            <el-table
              :header-cell-style="{background:'#eef1f6'}"
              :row-class-name="tableRowClassName"
              ref="multipleTable"
              :data="not_class_student"
              tooltip-effect="dark"
              style="min-height: 400px;border: 3px solid rgba(0, 0, 0, 0.397);"
              @selection-change="not_in_Course">
              <el-table-column
                type="selection"
                width="190%">
              </el-table-column>
              <el-table-column
                label="學生名字"
                >
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        <el-col :offset="3" :span="8">
          <div class="grid-content2">
            <el-table
              :header-cell-style="{background:'#eef1f6'}"
              ref="multipleTable"
              :data="class_student"
              tooltip-effect="dark"
              style="min-height: 400px;"
              @selection-change="in_Course">
              <el-table-column
                type="selection"
                width="190%">
              </el-table-column>
              <el-table-column
                label="學生名字"
                >
                <template slot-scope="scope">{{ scope.row.name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
      <div style="margin: 20px;"></div>
      <el-row :gutter="30">
        <el-col :offset="3" :span="5">
          <div class="grid-content2">
            <el-button type="warning" @click="addStudent()" class="button2">新增</el-button>
          </div>
        </el-col>
        <el-col :offset="6" :span="5">
          <div class="grid-content2">
            <el-button type="warning"  @click="deleteStudent()" class="button2">刪除</el-button>
          </div>
        </el-col>
      </el-row>
    </el-main>
    <el-footer class="iner1">
      </el-footer>
    <!-- </div> -->
  </el-container>
</template>

<script>
import { getUser } from '@/api/user'
import { addStudent, deleteStudent } from '@/api/student'
export default {
  name: 'CourseAccount',
  inject: ['reload'],
  data () {
    return {
      class_student: [],
      not_class_student: [],
      seleted: []
    }
  },
  created () {
    getUser().then(res => {
      this.class_student = []
      this.not_class_student = []
      for (let i = 0; i < res.data.data.Users.length; i++) {
        for (let j = 0; j < res.data.data.Users[i].CLASSES.split(',').length; j++) {
          if (res.data.data.Users[i].CLASSES.split(',')[j] === this.$store.state.class_id) {
            this.class_student.push(res.data.data.Users[i])
          }
        }
      }
      for (let i = 0; i < res.data.data.Users.length; i++) {
        let check = true
        for (let j = 0; j < this.class_student.length; j++) {
          if (res.data.data.Users[i].name === this.class_student[j].name) {
            check = false
          }
        }
        if (check && res.data.data.Users[i].role === 'student') {
          this.not_class_student.push(res.data.data.Users[i])
        }
      }
    })
  },
  methods: {
    not_in_Course (val) {
      this.seleted = val
    },
    in_Course (val) {
      this.seleted = val
    },
    addStudent () {
      for (let i = 0; i < this.seleted.length; i++) {
        addStudent({
          class_id: this.$store.state.class_id,
          user_id: this.seleted[i].id
        }).then(res => {
          this.reload()
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'success'
          })
        }).catch(error => {
          this.$message({
            showClose: true,
            message: error,
            type: 'warning'
          })
          this.reload()
        })
      }
    },
    deleteStudent () {
      for (let i = 0; i < this.seleted.length; i++) {
        deleteStudent({
          class_id: this.$store.state.class_id,
          user_id: this.seleted[i].id
        }).then(res => {
          this.reload()
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'success'
          })
        }).catch(error => {
          this.$message({
            showClose: true,
            message: error,
            type: 'warning'
          })
          this.reload()
        })
      }
    },
    tableRowClassName ({row, rowIndex}) {
      return 'row1'
    }
  }
}
</script>

<style>
.el-col1 {
  position: relative;
  left: 150px;
}

.grid-content1 {
  font-size: 30px;
  background-color: orange;
  position: relative;
}

.grid-content2 {
  font-size: 30px;
  position: relative;
  left: 150px;
}

.iner1 {
  background-color: #EDEDED;
}

.button2{
  background-color: orange;
  color: black;
  font-size: 20px;
}

.el-table{
  background: white;
}

.el-table .row1 {
  background: white;
}
</style>
