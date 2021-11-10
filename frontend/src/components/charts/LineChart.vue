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
      let dataSets = this.comparisonDataSets;
      let labels = this.comparisonLabels;

      this.datacollection.labels = labels;
      this.datacollection.datasets = dataSets;

      this.renderChart(this.datacollection, this.options);
    },
  },
  data() {
    return {
      options: {
        scales: {
          yAxes: [
            { ticks: { beginAtZero: true }, gridLines: { display: true } },
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
  mounted() {
    let dataSets = this.comparisonDataSets;
    let labels = this.comparisonLabels;

    this.datacollection.labels = labels;
    this.datacollection.datasets = dataSets;

    this.renderChart(this.datacollection, this.options);
  },
};
</script>
