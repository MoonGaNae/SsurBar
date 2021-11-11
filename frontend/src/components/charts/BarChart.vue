<script>
import { Bar } from "vue-chartjs";
import { mapState } from "vuex";

export default {
  extends: Bar,
  name: "BarChart",
  props: ["chartData"],
  watch: {
    barLabels() {
      let dataSets = this.barDataSets;
      let labels = this.barLabels;

      this.datacollection.labels = labels;
      this.datacollection.datasets = dataSets;

      console.log(this.datacollection);

      this.renderChart(this.datacollection, this.options);
    },
  },
  computed: {
    ...mapState("analysis", ["barLabels", "barDataSets"]),
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
              stacked: true,
            },
          ],
          xAxes: [
            {
              gridLines: {
                display: false,
              },
              stacked: true,
              tacked: false,
              beginAtZero: true,
            },
          ],
        },
        legend: {
          onClick: function () {},
        },
        tooltips: {
          callbacks: {
            label: function (tooltipItem, data) {
              return (
                "score : " +
                data["datasets"][tooltipItem.datasetIndex]["data"][
                  tooltipItem["index"]
                ]
              );
            },
          },
        },
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  mounted() {
    let dataSets = this.barDataSets;
    let labels = this.barLabels;

    this.datacollection.labels = labels;
    this.datacollection.datasets = dataSets;

    this.renderChart(this.datacollection, this.options);
  },
};
</script>
