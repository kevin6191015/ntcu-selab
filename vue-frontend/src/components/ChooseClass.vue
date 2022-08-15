<template>
    <el-container>
        <el-header>
            <h2 class="grid-content bg-purple-dark" align="center">課程選擇</h2>
        </el-header>
        <el-main>
            <el-select v-model="class_name" placeholder="課程名稱">
                <el-option
                v-for="item in content"
                :key="item.class_name"
                :label="item.class_name"
                :value="item.class_name">
                </el-option>
            </el-select>
        </el-main>
    </el-container>
</template>

<script>
import { getCourse } from '../api/course'
import store from '@/store'
export default {
  name: 'chooseclass',
  data () {
    return {
      content: [],
      class_name: ''
    }
  },
  created () {
    getCourse().then(res => {
      this.content = JSON.parse(JSON.stringify(res.data.Courses))
    }).catch(error => {
      this.$alert(JSON.parse(JSON.stringify(error)).message, JSON.parse(JSON.stringify(error)).name, {
        confirmButtonText: '確定'
      })
      var path = this.$route.query.redirect
      this.$router.replace({
        path: path === '/' || path === undefined ? '/chooseclass' : path})
    })
  },
  updated () {
    store.commit('SET_CLASS', this.class_name)
  }
}
</script>

<style>
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
    font-size:24px;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
