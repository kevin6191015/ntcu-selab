<template>
  <el-container>
    <el-main class="container outer">
      <el-row :gutter="100" style="padding: 5%;">
        <el-col :span="12"><div class="grid-content">未加入帳號</div></el-col>
        <el-col :span="12"><div class="grid-content">已加入帳號</div></el-col>
      </el-row>
      <el-row :gutter="100">
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
                <template slot-scope="scope">{{ scope.row.student_name }}</template>
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
                <template slot-scope="scope">{{ scope.row.student_name }}</template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { getStudent } from '@/api/student'
export default {
  name: 'CourseAccount',
  data () {
    return {
      class_student: [],
      multipleSelection: []
    }
  },
  created () {
    getStudent({class_id: 2}).then(res => {
      this.class_student = res.data.data.Students
    })
  },
  methods: {
    handleSelectionChange (val) {
      this.multipleSelection = val
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

.el-col {
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
  background-color: rgb(111, 122, 144);
  height: 700px;
  padding: 15px;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}
.el-icon-arrow-down {
    font-size: 13px;
}
</style>
