
<script>
  import { Bar } from 'vue-chartjs'


  export default {
    extends: Bar,
    name: "BarChart",
    data () {
      return {
        datacollection: {
          labels: ['1번 문항', '2번 문항', '3번 문항', '4번 문항', '5번 문항', '6번 문항', '7번 문항', '8번 문항', '9번 문항', '10번 문항', '11번 문항r', '12번 문항','13번 문항','14번 문항'],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f87979',
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              data: [40, 20, 30, 50, 90, 10, 20, 40, 50, 70, 90, 90, 10,20]
            }
          ]
        },
        options: {
          scales: {
            yAxes: [{
              beginAtZero: true,
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false
              },
              tacked: false,
              beginAtZero: true,
              scaleLabel: {
                  labelString: 'Month'
              },
              ticks: {
                  autoSkip: false
              }
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
        
      }
    },
    mounted () {
      this.renderChart(this.datacollection, this.options)
    }
  }
</script>
