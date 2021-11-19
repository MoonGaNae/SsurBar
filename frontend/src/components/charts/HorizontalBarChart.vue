<script>
import { HorizontalBar } from "vue-chartjs";

var dynamicColors = function() {
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return "rgb(" + r + "," + g + "," + b + ")";
    }

export default {
  extends: HorizontalBar,
  name: "HorizontalBarChart",

  data() {
    return {
      datacollection: {
        labels: [
          "프론트엔드",
          "벡엔드",
          "어쩌구",
          "저쩌구",
          "저쩌구",
          "저쩌구",
          "저쩌구",
          "저쩌구",         
        ],
        datasets: [
          {
            label: "Result Data",
            backgroundColor: dynamicColors,
            pointBackgroundColor: "white",
            borderWidth: 1,
            pointBorderColor: "#249EBF",
            data: [4, 2, 3, 5, 1, 1.2,2,5],
          },
        ],
      },
      options: {
        scales: {
          xAxes: [
            {
              beginAtZero: true,
              ticks: {
                beginAtZero: true,
                min: 0,
                max: 5,
                stepSize: 0.5,
              },
              gridLines: {
                display: true,
              },
            },
          ],
          yAxes: [
            {
              gridLines: {
                display: false,
              },
              tacked: false,
              beginAtZero: true,
              
              ticks: {
                autoSkip: false,
              },
              
            },
          ],
        },
        legend: {
          display: true,
        },
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  mounted() {
    this.renderChart(this.datacollection, this.options);
  },
};
</script>
