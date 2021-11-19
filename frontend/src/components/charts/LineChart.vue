<script>
import { Line } from "vue-chartjs";
import { mapState } from "vuex";

export default {
  //   name: "LineChart",
  extends: Line,
  computed: {
    ...mapState("analysis", [
      "answerDataList",
      "comparisonLabels",
      "comparisonDataSets",
    ]),
  },
  watch: {
    comparisonDataSets() {
      this.makeLineChart();
    },
  },
  data() {
    return {
      options: {
        scales: {
          yAxes: [
            {
              ticks: { beginAtZero: true, min: -5, max: 5, stepSize: 0.5 },
              gridLines: { display: true },
            },
          ],
          xAxes: [{ gridLines: { display: false } }],
        },
        legend: { display: true },
        responsive: true,
        maintainAspectRatio: false,
      },
      datacollection: {
        labels: [],
        datasets: [],
      },
    };
  },
  methods: {
    makeLineChart() {
      let labels = this.comparisonLabels;
      let dataSets = [];

      if (this.comparisonDataSets.length > 1) {
        let mainDataSet = this.comparisonDataSets[0].data;

        for (let idx = 1; idx < this.comparisonDataSets.length; idx++) {
          let scoreList = [];

          this.comparisonDataSets[idx].data.forEach((el, scoreIdx) => {
            scoreList.push(mainDataSet[scoreIdx] - el);
          });

          let dataSet = {
            backgroundColor: this.comparisonDataSets[idx].backgroundColor,
            borderColor: this.comparisonDataSets[idx].borderColor,
            borderWidth: this.comparisonDataSets[idx].borderWidth,
            data: scoreList,
            fill: this.comparisonDataSets[idx].fill,
            label: this.comparisonDataSets[idx].label,
            pointBackgroundColor:
              this.comparisonDataSets[idx].pointBackgroundColor,
            pointBorderColorthis:
              this.comparisonDataSets[idx].pointBackgroundColor,
          };

          dataSets.push(dataSet);
        }
        this.datacollection.datasets = dataSets;
      } else {
        this.datacollection.datasets = [];
      }

      this.datacollection.labels = labels;

      this.renderChart(this.datacollection, this.options);
    },
  },
  mounted() {
    this.makeLineChart();
  },
};
</script>
