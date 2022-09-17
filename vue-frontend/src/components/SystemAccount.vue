<template>
    <el-container>
        <el-main class="outer">
          <div style="margin: 20px;"></div>
          <el-row>
            <el-col :span="12">
              <el-table
                :data="tableData"
                stripe
                style="width: 100%"
                @selection-change="handlechange">
                <el-table-column
                  type="selection"
                  width="45">
                </el-table-column>
                <el-table-column
                  prop="id"
                  label="學號"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="username"
                  label="帳號"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="password"
                  label="密碼">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="名字">
                </el-table-column>
                <el-table-column
                  prop="role"
                  label="帳號類型">
                </el-table-column>
                <el-table-column
                  prop="email"
                  label="e-mail">
                </el-table-column>
              </el-table>
            </el-col>
            <el-col :span="12">
              <el-row>
                  <el-select v-model="account_type" placeholder="請選擇帳號類型" class="box">
                    <el-option
                      v-for="item in auth"
                      :key="item.value"
                      :label="item.label"
                      :value="item.label">
                    </el-option>
                  </el-select>
              </el-row>
              <el-row>
                <el-input
                v-model="id"
                placeholder="學號"
                style="margin: 20px; width: 90%;"></el-input>
              </el-row>
              <el-row>
                <el-input
                v-model="name"
                placeholder="名字"
                style="margin: 20px; width: 90%;"></el-input>
              </el-row>
              <el-row>
                <el-input
                v-model="userName"
                placeholder="帳號"
                style="margin: 20px; width: 90%;"></el-input>
              </el-row>
              <el-row>
                <el-input
                v-model="password"
                placeholder="密碼"
                style="margin: 20px; width: 90%;"></el-input>
              </el-row>
              <el-row>
                <el-input
                  v-model="email"
                  placeholder="e-mail"
                  style="margin: 20px; width: 90%;">
                </el-input>
              </el-row>
              <el-row style="display: flex;">
                  <el-button type="warning" style="margin: 32px; font-size: 20px" @click="add()">新增</el-button>
                  <el-button type="warning" style="margin: 32px; font-size: 20px" @click="update()">修改</el-button>
                  <el-upload
                  action="/data/user/upload?file="
                  :before-upload="onBeforeUpload"
                  :headers="uploadData"
                  :on-error="uploadError"
                  :on-success="uploadSuccess"
                  :show-file-list="false">
                    <el-button type="warning" style="margin: 32px; font-size: 20px;">匯入</el-button>
                  </el-upload>
                  <el-button type="warning" style="margin: 32px; font-size: 20px" @click="deleted()">刪除</el-button>
              </el-row>
            </el-col>
          </el-row>
        </el-main>
    </el-container>
</template>

<script>
import { addUser, deleteUser, updateUser, getUser } from '@/api/user'
export default {
  name: 'SystemAccount',
  inject: ['reload'],
  data () {
    return {
      auth: [{
        value: 'student',
        label: 'student'
      }, {
        value: 'teacher',
        label: 'teacher'
      }, {
        value: 'TA',
        label: 'TA'
      }],
      account_type: '',
      userName: '',
      password: '',
      role: '',
      email: '',
      name: '',
      id: '',
      tableData: [],
      accountList: [],
      uploadData: {
        'Authorization': 'Bearer ' + this.$store.state.token
      }
    }
  },
  created () {
    getUser().then(res => {
      this.tableData = res.data.data.Users
    })
  },
  methods: {
    add () {
      if (this.account_type === '') {
        this.$alert('請選擇帳號類型', 'Error', {
          confirmButtonText: '確定'
        })
      } else {
        addUser({
          userName: this.userName,
          password: this.password,
          role: this.account_type,
          email: this.email,
          name: this.name,
          id: this.id
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
    deleted () {
      if (this.accountList.length) {
        for (let i = 0; i < this.accountList.length; i++) {
          if (this.accountList[i].role === 'student') {
            deleteUser({
              id: this.accountList[i].id
            }).then(res => {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: 'success'
              })
              this.reload()
            }).catch(error => {
              this.$message({
                showClose: true,
                message: error,
                type: 'warning'
              })
              this.reload()
            })
          } else {
            this.$message({
              showClose: true,
              message: 'You can not remove this account(It is tacher or TA)',
              type: 'warning'
            })
            this.reload()
          }
        }
      }
      if (this.id !== '') {
        deleteUser({
          id: this.id
        }).then(res => {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'success'
          })
          this.reload()
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
    update () {
      updateUser({
        id: this.id,
        name: this.name,
        userName: this.userName,
        password: this.password,
        email: this.email
      }).then(res => {
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
      })
    },
    onBeforeUpload (file) {
      if (file.type !== 'text/csv') {
        this.$message.error('格式錯誤')
      }
    },
    uploadError (res, file, filelist) {
      this.$message.error('上傳失敗')
    },
    uploadSuccess (file) {
      this.$message.success('上傳成功')
      this.reload()
    },
    handlechange (selected) {
      this.accountList = selected
    }
  }
}
</script>

<style>
.d-flex {
  display: flex;
  flex-wrap: wrap;
}

.outer {
  background-color: rgba(111, 122, 144, 0.555);
  padding: 15px;
}

.box {
  width: 90%;
  margin: 20px;
}
</style>
