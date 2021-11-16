<script>
import { Radar } from "vue-chartjs";
import { mapState } from "vuex";

export default {
  extends: Radar,
  watch: {
    radarLabels() {
      let dataSets = this.radarDataSets;
      let labels = this.radarLabels;

      this.datacollection.labels = labels;
      this.datacollection.datasets = dataSets;

      this.renderChart(this.datacollection, this.options);

      console.log(this.datacollection);
    },
  },
  computed: {
    ...mapState("analysis", ["answerDataList", "radarLabels", "radarDataSets"]),
  },
  data() {
    return {
      datacollection: {
        labels: null,
        datasets: null,
      },
      options: {
        legend: {
          display: false,
        },
        maintainAspectRatio: false,
        scale: {
          angleLines: {
            display: false,
          },
          ticks: {
            suggestedMin: 0,
            suggestedMax: 5,
          },
          pointLabels: {
            fontSize: 18,
          },
        },
      },
    };
  },
  mounted() {
    let dataSets = this.radarDataSets;
    let labels = this.radarLabels;

    this.datacollection.labels = labels;
    this.datacollection.datasets = dataSets;
    this.renderChart(this.datacollection, this.options);

    console.log(this.datacollection);
  },
};
</script>
