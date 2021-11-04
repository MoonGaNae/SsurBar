<script>
  import { Radar } from 'vue-chartjs'

  export default {
    extends: Radar,
    data () {
      return {
        datacollection: {
          labels: ['1번 항목', '2번 항목', '3번 항목', '4번 항목','5번 항목'],
          datasets: [
            {
              label: 'Dataset',
              backgroundColor: "rgba(255, 10, 13, 0.4)",
              pointBackgroundColor: 'white',
              borderWidth: 5,
              pointBorderColor: '#249EBF',
              data: [5,6,7,4,4],
              
            },
            // {
            //   label: 'Dataset2',
            //   fill:true,
            //   backgroundColor: "rgba(25, 10, 13, 0.4)",
            //   pointBackgroundColor: 'white',
            //   borderWidth: 1,
            //   pointBorderColor: '#249EBF',
            //   data: [8,6,8,8,8],
              
            // }
          ]
        },
        options: {
        scale: {
          angleLines: {
            display: false
          },
          ticks: {
            suggestedMin: 1,
            suggestedMax: 8
          }}}
      };
    },
    mounted () {
      this.renderChart(this.datacollection, this.options)
    }
  }
</script>
