<template>
    <div style=" background-color: rgba(111, 122, 144, 0.555)">
      <div  style="height: 1250px">
        <div class="item" align="center">
            <div id="qname-green">
              <h3>班級作答人數曲線圖</h3>
            </div>
            <canvas id="myChart" class="item"></canvas>
        </div>
        <div style="height: 200px"></div>
        <div class="item1" align="center">
          <div id="qname-green">
              <h3>班級作答分數分布</h3>
            </div>
            <canvas id="myChart1" class="item" ></canvas>
        </div>
      </div>
      <div class="footer">
        <el-button  @click='goback'>關閉視窗</el-button>
      </div>
    </div>
</template>
<script>
import { getEveryRange, getAnsweredEveryday } from '@/api/classscore'
import { getStudent } from '@/api/student'
export default {
  data () {
    return {
      content: [],
      project_name: this.$store.state.project_name,
      TotalPeople: ''
    }
  },
  created () {
    getAnsweredEveryday(
      {
        project_name: this.$store.state.project_name
      }
    ).then(res => {
      var day = []
      var AnsweredToday = []
      for (let i = 0; i < res.data.data['Answered Everyday'].length; i++) {
        AnsweredToday.push(res.data.data['Answered Everyday'][i].answered_today)
        day.push(res.data.data['Answered Everyday'][i].day)
      }
      for (let i = 1; i < AnsweredToday.length; i++) {
        AnsweredToday[i] += AnsweredToday[i - 1]
      }
      const ctx = document.getElementById('myChart')
      const data = {
        labels: day,
        datasets: [{
          label: 'total people submissions',
          data: AnsweredToday,
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0
        }]
      }
      const myChart = new Chart(ctx, { //eslint-disable-line
        type: 'line',
        data: data,
        options: {
          scales: {
            yAxes: [{
              ticks: {
                min: 0,
                suggestedMax: 5,
                beginAtZero: true,
                stepSize: 1
              }
            }]
          }
        }
      })
    })
    getStudent({
      class_id: this.$store.state.class_id
    }).then(res => {
      this.TotalPeople = res.data.data['Students'].length
    })
    getEveryRange(
      {
        project_name: this.$store.state.project_name
      }
    ).then(res => {
      var AnsweredPeople = res.data.data['Answered Everyday'][0].sixty_down + res.data.data['Answered Everyday'][0].sixty_seventy + res.data.data['Answered Everyday'][0].seventy_eighty + res.data.data['Answered Everyday'][0].eighty_ninty + res.data.data['Answered Everyday'][0].ninty_hundred + res.data.data['Answered Everyday'][0].hundred
      var people = []
      people.push(this.TotalPeople - AnsweredPeople)
      people.push(res.data.data['Answered Everyday'][0].sixty_down === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].sixty_down)
      people.push(res.data.data['Answered Everyday'][0].sixty_seventy === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].sixty_seventy)
      people.push(res.data.data['Answered Everyday'][0].seventy_eighty === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].seventy_eighty)
      people.push(res.data.data['Answered Everyday'][0].eighty_ninty === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].eighty_ninty)
      people.push(res.data.data['Answered Everyday'][0].ninty_hundred === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].ninty_hundred)
      people.push(res.data.data['Answered Everyday'][0].hundred === 0 ? 'undefine' : res.data.data['Answered Everyday'][0].hundred)
      const ctx1 = document.getElementById('myChart1')
      const data1 = {
        labels: [
          'unanswered',
          '0 ~ 60',
          '60 ~ 70',
          '70 ~ 80',
          '80 ~ 90',
          '90 ~ 100',
          '100'
        ],
        datasets: [{
          label: 'My First Dataset',
          data: people,
          backgroundColor: [
            '#004c6d',
            '#2d6484',
            '#4c7c9b',
            '#6996b3',
            '#86b0cc',
            '#a3cbe5',
            '#c1e7ff'
          ],
          hoverOffset: 4
        }]
      }
      const myChart1 = new Chart(ctx1, { //eslint-disable-line
        type: 'pie',
        data: data1
      })
    })
  },
  methods: {
    goback () {
      window.close()
    }
  }
}
</script>
<style>
#qname-green{
  margin-left:1%;
  width:fit-content;
  padding: 10px;
  text-align:left;
  border: 3px solid rgba(0, 0, 0, 0.397);
  background-color: rgba(228, 228, 228, 0.651);
  border-radius: 12px;
  position: relative;
  top: 30px;
  left: 160px;
}
.item {
  width: 700px;
  height: 400px;
}
.item1 {
  width: 700px;
  height: 400px;
}
canvas{
  position: relative;
  top: 60px;
  left: 160px;
  background-color: #fff;
  padding: 20px 20px 20px 20px;
  width: 40px;
}
.flex{
  width: 400px;
}
.footer{
  clear:both;
  height:80px;
  text-align:right;
  line-height:80px;
  margin-right:2%;
}
</style>
