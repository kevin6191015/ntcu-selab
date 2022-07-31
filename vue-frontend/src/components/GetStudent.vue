<template>
    <div>
      <table class="table table-striped" >
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">student id</th>
            <th scope="col">student name</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in this.content" :key="index">
            <th scope="row">{{index+=1}}</th>
              <td>{{item.student_id}}</td>
              <td>{{item.student_name}}</td>
          </tr>
        </tbody>
      </table>
      <!-- <table>
          <tbody>
              <tr v-for="(item, key) in this.content" :key="key">
                 <td>student id : {{item.student_id}}</td>
                 <td>student name : {{item.student_name}}</td>
               </tr>
           </tbody>
      </table> -->
    </div>
</template>
<script>
import {getStudent} from '../api/student'
export default {
  name: 'GetStudent',
  data () {
    return {
      content: []
    }
  },
  created () {
    getStudent({
    }).then(res => {
      var a = JSON.stringify(res.data.Students)
      this.content = JSON.parse(a)
    }).catch(function (error) {
      console.log(error)
    })
  }
}
</script>
<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    height: 100%;
  }
  .layout-logo{
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: 20px;
    font-weight: bold;
    text-align: center;
    color: #49ffcc;
  }
  .layout-nav{
    width: 420px;
    margin: 0 auto;
    margin-right: 20px;
  }
  .layout-footer-center{
    text-align: center;
  }
</style>
