<template>
  <el-container class="container2" :style="{height: scrollerHeight}">
    <el-main>
      <div class="assignment">作業一覽</div>
      <el-row>
        <el-table
          :data="content"
          highlight-current-row
          @current-change="seleted_class"
          @selection-change="handleSelectionChange"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="assignment_name"
            label="作業名稱"
            width="250">
          </el-table-column>
          <el-table-column
            prop="release_time"
            label="公布日期">
          </el-table-column>
          <el-table-column
            prop="deadline"
            label="截止日期">
          </el-table-column>
          <el-table-column
          label="更新作業">
            <template slot-scope="scope">
              <el-button @click="Update(scope.row)" type="text" size="small">修改該次作業</el-button>
            </template>
          </el-table-column>
          <el-table-column
            prop="release_or_not"
            label="公布與否"
            :filters="[{ text: '已公布', value: '已公布' }, { text: '未公布', value: '未公布' }]"
            :filter-method="filterTag"
            filter-placement="bottom-end">
            <template slot-scope="scope">
              <el-tag
                disable-transitions>{{scope.row.release_or_not}}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
      <div id="footer-left2">
      <el-tooltip effect="dark" content="勾選作業並刪除" placement="top">
        <el-button type="text"  icon="el-icon-question" circle></el-button>
      </el-tooltip>
      <el-button  @click='Delete'>刪除作業</el-button>
    </div>
    </el-main>
  </el-container>
</template>

<script>
import {getAllAssignments, DeleteAssignment} from '@/api/assignment'
export default {
  name: 'ShowAssignment',
  data () {
    return {
      multipleSelection: [],
      content: []
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
        let time = parseInt(tmp[i].release_time.substring(0, 4)) * 12 * 365 + parseInt(tmp[i].release_time.substring(5, 7)) * 31 + parseInt(tmp[i].release_time.substring(8, 10))
        var Today = new Date()
        let now = parseInt(Today.getFullYear()) * 12 * 365 + parseInt(Today.getMonth() + 1) * 31 + parseInt(Today.getDate())
        if (now >= time) {
          if (this.content.length > 0) {
            let check = true
            for (let j = 0; j < this.content.length; j++) {
              if (this.content[j].assignment_name === tmp[i].assignment_name) {
                check = false
              }
            }
            if (check) {
              tmp[i].release_or_not = '已公布'
              this.content.push(tmp[i])
            }
          } else {
            tmp[i].release_or_not = '已公布'
            this.content.push(tmp[i])
          }
        } else {
          if (this.content.length > 0) {
            let check = true
            for (let j = 0; j < this.content.length; j++) {
              if (this.content[j].assignment_name === tmp[i].assignment_name) {
                check = false
              }
            }
            if (check) {
              tmp[i].release_or_not = '未公布'
              this.content.push(tmp[i])
            }
          } else {
            tmp[i].release_or_not = '未公布'
            this.content.push(tmp[i])
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
      if (this.$store.state.role === 'student') {
        this.$router.replace({
          path: '/ShowStudentHomework'
        })
      } else {
        this.$router.replace({
          path: '/ShowHomeWork'
        })
      }
    },
    Update (row) {
      this.$store.commit('SET_SELECTEDQUESTION', '')
      this.$store.commit('SET_PUBLISHEDQUESTION', row.assignment_name)
      this.$router.replace({
        path: '/UpdateAssignment'
      })
    },
    handleSelectionChange (theval) {
      this.multipleSelection = theval
    },
    filterTag (value, row) {
      return row.release_or_not === value
    },
    Delete () {
      if (this.multipleSelection) {
        var temp = this.content
        var multi = this.multipleSelection
        for (let j = 0; j < this.multipleSelection.length; j++) {
          getAllAssignments({
            cid: this.$store.state.class_id
          }).then(res => {
            let tmp = res.data.data.Assignments
            for (let i = 0; i < tmp.length; i++) {
              if (tmp[i].assignment_name === multi[j].assignment_name) {
                DeleteAssignment({
                  cid: this.$store.state.class_id,
                  qid: tmp[i].question_id,
                  created_time: tmp[i].created_time
                })
              }
            }
          })
        }
        for (let j = 0; j < this.multipleSelection.length; j++) {
          var temp2 = []
          for (let k = 0; k < temp.length; k++) {
            if (temp[k].assignment_name !== this.multipleSelection[j].assignment_name) {
              temp2.push(temp[k])
            }
          }
          temp = temp2
        }
        // location.reload()
        this.content = temp
      }
    }
  },
  computed: {
    scrollerHeight: function () {
      return (window.innerHeight - 46 - 50 - 10) + 'px'
    }
  }
}
</script>

<style>
.container2 {
  background-color: #EDEDED;
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
.assignment {
  width: auto;
  float:left;
  font-size: 20px;
  padding: 10px;
  border: 4px solid rgba(0, 0, 0, 0.397);
  border-radius: 12px;
  margin-bottom: 20px;
  font-family: "Microsoft YaHei";
}
#footer-left2{
  clear:both;
  text-align:center;
  line-height:80px;
  float:right;
  }
</style>
