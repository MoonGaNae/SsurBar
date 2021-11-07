<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  name: "BarChart",
  props: ["chartData"],
  data() {
    return {
      datacollection: {
        labels: [
          "1번 문항",
          "2번 문항",
          "3번 문항",
          "4번 문항",
          "5번 문항",
          "6번 문항",
          "7번 문항",
          "8번 문항",
          "9번 문항",
          "10번 문항",
          "11번 문항r",
          "12번 문항",
          "13번 문항",
          "14번 문항",
        ],
        datasets: [
          {
            label: "Data One",
            backgroundColor: "#f87979",
            pointBackgroundColor: "white",
            borderWidth: 1,
            pointBorderColor: "#249EBF",
            data: [4, 2, 3, 5, 3, 1, 2, 4, 5, 1, 1.8, 2.2, 3.3, 2, 4.4, 4.3],
          },
        ],
      },
      options: {
        scales: {
          yAxes: [
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
          xAxes: [
            {
              gridLines: {
                display: false,
              },
              tacked: false,
              beginAtZero: true,
              // scaleLabel: {
              //   labelString: "Month",
              // },
              // ticks: {
              //   autoSkip: false,
              // },
            },
          ],
        },
        // legend: {
        //   display: true,
        // },
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
