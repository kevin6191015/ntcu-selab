<template>
    <el-container>
        <el-main class="container outer">
                <el-row >
                   <el-col style="display: flex;justify-content: center; align-items: center;">
                         <el-select v-model="account_type" placeholder="請選擇帳號類型" class="box">
                            <el-option
                            v-for="item in auth"
                            :key="item.value"
                            :label="item.label"
                            :value=item.value>
                        </el-option>
                    </el-select>
                   </el-col>
                </el-row>
                <el-row>
                  <el-col style="display: flex;justify-content: center; align-items: center;">
                    <el-input
                    v-model="id"
                    placeholder="學號"
                    style="margin: 20px;width:50%"></el-input>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="display: flex;justify-content: center; align-items: center;">
                    <el-input
                    v-model="name"
                    placeholder="名字"
                    style="margin: 20px;width:50%"></el-input>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="display: flex;justify-content: center; align-items: center;">
                    <el-input
                    v-model="userName"
                    placeholder="帳號"
                    style="margin: 20px;width:50%"></el-input>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="display: flex;justify-content: center; align-items: center;">
                    <el-input
                    v-model="password"
                    placeholder="密碼"
                    style="margin: 20px;width:50%"></el-input>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="display: flex;justify-content: center; align-items: center;">
                    <el-input
                    v-model="email"
                    placeholder="e-mail"
                    style="margin: 20px;width:50%"></el-input>
                  </el-col>
                </el-row>
            <div style="margin: 20px;"></div>
            <el-col style="display: flex;justify-content: center; align-items: center;">
                <el-button type="warning" style="margin: 20px; font-size: 20px;" @click="add()">新增</el-button>
                <el-upload
                :action="uploadActionUrl()"
                :before-upload="onBeforeUpload"
                :headers="uploadData"
                :onError="uploadError"
                :onSuccess="uploadSuccess"
                show-file-list=false>
                  <el-button type="warning" style="margin: 20px; font-size: 20px;">匯入</el-button>
                </el-upload>
                <el-button type="warning" style="margin: 20px; font-size: 20px;" @click="update()">修改</el-button>
                <el-button type="warning" style="margin: 20px; font-size: 20px;" @click="deleted()">刪除</el-button>
            </el-col>
        </el-main>
    </el-container>
</template>

<script>
import { addUser, deleteUser, updateUser } from '@/api/user'
import store from '../store'
export default {
  name: 'SystemAccount',
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
      batch: [],
      userName: '',
      password: '',
      role: '',
      email: '',
      name: '',
      id: '',
      fileList: [],
      uploadData: {
        'Authorization': 'Bearer ' + store.state.token
      }
    }
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
      deleteUser({
        id: this.id
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
    uploadActionUrl (file) {
      return '/data/user/upload?file=' + file
    },
    uploadError () {
      this.$message.error('上船失敗')
    },
    uploadSuccess () {
      this.$message.success('上船成功')
    }
  }
}
</script>

<style>
.container {
  width: 1250px;
  margin: 0 auto;
}

.d-flex {
  display: flex;
  flex-wrap: wrap;
}

.outer {
  background-color: rgba(111, 122, 144, 0.555);
  height: 650px;
  padding: 15px;
}

.box {
  width: 50%;
  margin: 35px;
}
</style>
