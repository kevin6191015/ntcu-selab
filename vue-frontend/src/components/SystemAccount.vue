<template>
    <el-container>
        <el-main class="container outer">
            <div>
                <el-row>
                   <el-col :span="12">
                         <el-select v-model="account_type" placeholder="請選擇帳號類型" class="box">
                            <el-option
                            v-for="item in auth"
                            :key="item.value"
                            :label="item.label"
                            :value="item.label">
                        </el-option>
                    </el-select>
                   </el-col>
                </el-row>
                <div style="margin: 20px 0;"></div>
                <el-row>
                    <el-col :span="12">
                        <el-input v-model = "batch"
                            type="textarea"
                            :autosize="{ minRows: 26, maxRows: 40}"
                            placeholder="批次創建帳號">
                        </el-input>
                    </el-col>
                    <el-col :span="12">
                        <el-row>
                            <el-col :span="21">
                                <el-input
                                v-model="id"
                                placeholder="學號"
                                style="margin: 20px;"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="21">
                                <el-input
                                v-model="name"
                                placeholder="名字"
                                style="margin: 20px;"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="21">
                                <el-input
                                v-model="userName"
                                placeholder="帳號"
                                style="margin: 20px;"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="21">
                                <el-input
                                v-model="password"
                                placeholder="密碼"
                                style="margin: 20px;"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="21">
                                <el-input
                                v-model="email"
                                placeholder="e-mail"
                                style="margin: 20px;"></el-input>
                            </el-col>
                        </el-row>
                    </el-col>
                    <div style="margin: 20px;"></div>
                    <el-col :span="12">
                        <el-button type="warning" style="margin: 20px; font-size: 20px" @click="add()">新增</el-button>
                        <el-button type="warning" style="margin: 20px; font-size: 20px ;float:right">匯入</el-button>
                    </el-col>
                    <el-col :span="12">
                        <el-button type="warning" style="margin: 20px; font-size: 20px" @click="update()">修改</el-button>
                        <el-button type="warning" style="margin: 20px; font-size: 20px ;float:right" @click="deleted()">刪除</el-button>
                    </el-col>
                </el-row>
            </div>
        </el-main>
    </el-container>
</template>

<script>
import { addUser, deleteUser, updateUser } from '@/api/user'
export default {
  name: 'SystemAccount',
  data () {
    return {
      auth: [{
        value: '1',
        label: 'student'
      }, {
        value: '2',
        label: 'teacher'
      }, {
        value: '3',
        label: 'TA'
      }],
      account_type: '',
      batch: [],
      userName: '',
      password: '',
      role: '',
      email: '',
      name: '',
      id: ''
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
            message: res.data.message,
            type: 'succes'
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
          type: 'succes'
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
          type: 'succes'
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

.outer {
  background-color: rgba(111, 122, 144, 0.555);
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
