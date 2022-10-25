<template>
    <el-container class="iner1">
      <el-main>
        <el-row>
          <el-col :span="12">
            <span style="margin-left: 43%; font-size: 23px;">目前全部課程</span>
          <el-table
            :data="allcourse"
            style="width: 100%;margin-top: 20px;margin-left: 20px;"
            tooltip-effect="dark"
            highlight-current-row="true"
            @current-change="handlechange"
            @selection-change="handlechange2">
            <el-table-column v-if="Delete"
              type="selection">
            </el-table-column>
            <el-table-column
              prop="semester"
              label="學期">
            </el-table-column>
            <el-table-column
              prop="class_name"
              label="班級名稱">
            </el-table-column>
            <el-table-column
              prop="teacher"
              label="老師">
            </el-table-column>
            <el-table-column
              prop="TA"
              label="助教">
            </el-table-column>
            <el-table-column
              prop="semester"
              label="學期分類"
              :filters=allsemester
              :filter-method="filterTag"
              filter-placement="bottom-end">
              <template slot-scope="scope">
                <el-tag
                  disable-transitions>{{scope.row.semester}}</el-tag>
              </template>
            </el-table-column>
          </el-table>
          </el-col>
          <el-col :span="7" style="margin-left: 10%">
            <el-button @click="change2add">新增課程</el-button>
            <el-button style="margin-left: 15%;" @click="change2update">修改課程</el-button>
            <el-button style="margin-left: 15%;" @click="change2delete">刪除課程</el-button>
            <div v-if="Add" style="margin-top:5%;border: 3px solid black;background-color: rgb(200, 200, 200);">
              <span style="font-size: 30px;margin-left:35%;">新增課程</span>
              <el-input v-model="class_name" placeholder="請輸入課程名稱" style="margin:10%;width: 80%;"></el-input>
              <el-select v-model="seleted_teacher" placeholder="請選擇課程老師" style="margin-left:10px">
                <el-option
                  v-for="item in teacher"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-select v-model="seleted_TA" placeholder="請選擇課程助教">
                <el-option
                  v-for="item in TA"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-button style="margin-left:42%; margin-top:100px; margin-bottom: 5%;" @click="addcourse">新增</el-button>
            </div>
            <div v-if="Update" style="margin-top:5%;border: 3px solid black;background-color: rgb(200, 200, 200);">
              <div style="font-size: 30px;margin-left:35%;">修改課程</div>
              <div style="font-size: 10px;margin-left:29%;">(請在左方表格點選欲修改之課堂)</div>
              <el-table
                :data="seleted_class"
                style="margin: 10px; width: 95%"
                tooltip-effect="dark">
                <el-table-column
                  prop="semester"
                  label="學期">
                </el-table-column>
                <el-table-column
                  prop="class_name"
                  label="班級名稱">
                </el-table-column>
                <el-table-column
                  prop="teacher"
                  label="老師">
                </el-table-column>
                <el-table-column
                  prop="TA"
                  label="助教">
                </el-table-column>
              </el-table>
              <el-input v-model="class_name" placeholder="請輸入課程名稱" style="margin:10%;width: 80%;"></el-input>
              <el-select v-model="seleted_teacher" placeholder="請選擇課程老師" style="margin-left:10px">
                <el-option
                  v-for="item in teacher"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-select v-model="seleted_TA" placeholder="請選擇課程助教">
                <el-option
                  v-for="item in TA"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name">
                </el-option>
              </el-select>
              <el-button style="margin-left:42%; margin-top:100px; margin-bottom: 5%;" @click="updatecourse">修改</el-button>
            </div>
            <div v-if="Delete" style="margin-top:5%;border: 3px solid black;background-color: rgb(200, 200, 200);">
              <span style="font-size: 30px;margin-left:35%;">刪除課程</span>
              <el-table
                :data="seleted_classes"
                style="margin: 10px; width: 95%"
                tooltip-effect="dark">
                <el-table-column
                  prop="semester"
                  label="學期">
                </el-table-column>
                <el-table-column
                  prop="class_name"
                  label="班級名稱">
                </el-table-column>
                <el-table-column
                  prop="teacher"
                  label="老師">
                </el-table-column>
                <el-table-column
                  prop="TA"
                  label="助教">
                </el-table-column>
              </el-table>
              <el-button style="margin-left:42%; margin-top:100px; margin-bottom: 5%;" @click="deletecourse">刪除</el-button>
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
</template>

<script>
import { getAllCourse, AddCourse, getSemester, UpdateCourse, DeleteCourse } from '@/api/course'
import { getUser } from '@/api/user'
export default {
  name: 'ManageCourse',
  inject: ['reload'],
  data () {
    return {
      allcourse: [],
      class_name: '',
      teacher: [],
      TA: [],
      seleted_teacher: '',
      seleted_TA: '',
      seleted_class: [],
      seleted_classes: [],
      semester: '',
      Add: true,
      Delete: false,
      Update: false,
      allsemester: []
    }
  },
  created () {
    getAllCourse().then(res => {
      this.allcourse = res.data.data.Courses
    })
    getUser().then(res => {
      let tmp = res.data.data.Users
      for (let i = 0; i < tmp.length; i++) {
        if (tmp[i].role === 'teacher') {
          this.teacher.push(tmp[i])
        }
        if (tmp[i].role === 'TA') {
          this.TA.push(tmp[i])
        }
      }
    })
    getSemester().then(res => {
      let tmp = res.data.data.Semester
      for (let i = 0; i < tmp.length; i++) {
        this.allsemester.push({'text': tmp[i], 'value': tmp[i]})
      }
    })
    var Today = new Date()
    this.semester = Today.getFullYear() - 1911
    if (Today.getMonth() + 1 >= 8) {
      this.semester += '-2'
    } else {
      this.semester += '-1'
    }
  },
  methods: {
    addcourse () {
      if (this.class_name === '') {
        this.$alert('課程名稱不得為空!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else if (this.seleted_teacher === '') {
        this.$alert('請選擇課程老師!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else if (this.seleted_TA === '') {
        this.$alert('請選擇課程助教!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else {
        this.$confirm('確定新增?', '注意', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          AddCourse({
            courseName: this.class_name,
            semester: this.semester,
            teacher: this.seleted_teacher,
            ta: this.seleted_TA
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
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消新增'
          })
        })
      }
    },
    updatecourse () {
      if (this.class_name === '') {
        this.$alert('課程名稱不得為空!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else if (this.seleted_teacher === '') {
        this.$alert('請選擇課程老師!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else if (this.seleted_TA === '') {
        this.$alert('請選擇課程助教!!', '注意!', {
          confirmButtonText: '確定'
        })
      } else {
        this.$confirm('確定修改?', '注意', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          UpdateCourse({
            cid: this.seleted_class[0].class_id,
            courseName: this.class_name,
            teacher: this.seleted_teacher,
            ta: this.seleted_TA
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
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消修改'
          })
        })
      }
    },
    deletecourse () {
      this.$confirm('確定刪除?', '注意', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (let i = 0; i < this.seleted_classes.length; i++) {
          DeleteCourse({
            cid: this.seleted_classes[i].class_id
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
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消刪除'
        })
      })
    },
    handlechange (seleted) {
      this.seleted_class = [seleted]
    },
    handlechange2 (seleted) {
      this.seleted_classes = seleted
    },
    change2add () {
      this.Add = true
      this.Update = false
      this.Delete = false
    },
    change2update () {
      this.Add = false
      this.Update = true
      this.Delete = false
    },
    change2delete () {
      this.Add = false
      this.Update = false
      this.Delete = true
    },
    filterTag (value, row) {
      return row.semester === value
    }
  }
}
</script>

<style>
.iner1 {
  background-color: #EDEDED;
  height: 655px
}
</style>
