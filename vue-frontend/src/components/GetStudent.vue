<template>
    <div class="layout">
    <Layout>
      <Content :style="{padding: '0 50px'}">
        <Card>
          <div style="min-height: 200px;">
            <v-table
              is-horizontal-resize
              style="width:100%"
              :columns="columns"
              :table-data="tableData"
              row-hover-color="#eee"
              row-click-color="#edf7ff"
            ></v-table>
          </div>
        </Card>
      </Content>
    </Layout>
  </div>
</template>
<script>

export default {
  name: 'GetStudent',
  data () {
    return {
      tableData: [],
      columns: [
        {field: 'student_id', title: 'id', width: 80, titleAlign: 'center', columnAlign: 'center', isResize: true},
        {field: 'student_name', title: 'name', width: 280, titleAlign: 'center', columnAlign: 'left', isResize: true}
      ]
    }
  },
  created () {
    this.$ajax('data/student/getStudents?id=1').then(res => {
      var jsonObj = JSON.parse(JSON.stringify(res.data.data))
      //    var newArray = new Array()
      for (var i = 0; i < jsonObj.length; i++) {
        jsonObj[i].index = 'Students'
      }
      this.items[1].subs = jsonObj
      console.log(this.items[1].subs)
    //   this.tableData = jsonObj
    //   console.log(res.data)
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
