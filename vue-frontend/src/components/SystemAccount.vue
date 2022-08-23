<template>
    <el-container>
        <el-main class="container">
            <div class="d-flex outer">
                <el-select v-model="account_type" placeholder="請選擇帳號類型" class="box">
                    <el-option
                    v-for="item in auth"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    <span style="float: left; color: #8492a6; font-size: 23px">{{ item.label }}</span>
                    </el-option>
                </el-select>
                <div class="box"></div>
                <div class="box2">
                    <FormulateForm v-model = "student">
                        <FormulateInput type="textarea" name="sourcecode" placeholder="帳號" />
                    </FormulateForm>
                </div>
            </div>
        </el-main>
    </el-container>
</template>

<script>
export default {
  name: 'SystemAccount',
  data () {
    return {
      auth: [{
        value: '1',
        label: '學生'
      }, {
        value: '2',
        label: '管理者'
      }],
      account_type: ''
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
  background-color: rgb(197, 193, 193);
  height: 600px;
  padding: 15px;
}

.box {
  /* background-color: rgb(114, 8, 77); */
  flex: 0 0 30%;
  margin: 3px;
  color: aliceblue;
  display: flex;
  margin-left: 5%;
  justify-content: center;
  align-items: baseline;
}

.box2 {
  clear:both;
  margin-right: 5%;
  margin-left: 5%;
  text-align:center;
  line-height:80px;
  float:center;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}
.el-icon-arrow-down {
    font-size: 23px;
}
</style>
