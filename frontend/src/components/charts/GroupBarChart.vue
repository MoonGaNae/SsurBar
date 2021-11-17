<script>
import { HorizontalBar } from "vue-chartjs";
import { mapState } from "vuex";

export default {
  extends: HorizontalBar,
  name: "BarChart",
  watch: {
    comparisonDataSets() {
      this.makeGroupBarChart();
    },
  },
  computed: {
    ...mapState("analysis", ["barLabels", "barDataSets"]),
    ...mapState("analysis", [
      "answerDataList",
      "comparisonLabels",
      "comparisonDataSets",
    ]),
  },
  data() {
    return {
      datacollection: {
        labels: [],
        datasets: [],
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
              ticks: {
                beginAtZero: true,
                min: 0,
                max: 5,
                stepSize: 0.5,
              },
              beginAtZero: true,
            },
          ],
        },
        // responsive: true,
        maintainAspectRatio: true,
      },
    };
  },
  methods: {
    makeGroupBarChart() {
      let labels = this.comparisonLabels;
      let dataSets = [];

      this.comparisonDataSets.forEach((el, idx) => {
        let dataSet = {
          label: this.comparisonLabels[idx],
          data: el.data,
          borderColor: el.backgroundColor,
          backgroundColor: el.backgroundColor,
        };

        dataSets.push(dataSet);
      });
      this.datacollection.datasets = dataSets;
      this.datacollection.labels = labels;

      this.renderChart(this.datacollection, this.options);
    },
  },
  mounted() {
    this.makeGroupBarChart();
  },
};
</script>
