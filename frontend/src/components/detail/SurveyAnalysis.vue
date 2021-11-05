<template>
  <div class="chart-container">
    <div
      class="el-card box-card is-always-shadow"
      style="
        width: 90%;
        height: 95%;
        margin-left: 5%;
        margin-top: 3%;
        margin-bottom: 2%;
      "
    >
      <div class="chart-title">
        <h2>카테고리별 데이터</h2>
      </div>
      <div class="chart-div">
        <RadarChart />
      </div>
    </div>
    <div
      class="el-card box-card is-always-shadow bar-chart-container"
      style="
        width: 90%;
        height: 100%;
        margin-left: 5%;
        margin-top: 2%;
        margin-bottom: 2%;
      "
    >
      <div class="chart-title">
        <h2>문항별 데이터</h2>
      </div>
      <div class="bar-chart-div-parent">
        <div class="bar-chart-div" :style="{ width: widthTemp }">
          <BarChart />
        </div>
      </div>
    </div>
    <!-- <div>
      <canvas id="Bar" width="400" height="400"></canvas>
    </div>
    <div>
      <radar-chart></radar-chart>
    </div> -->
  </div>
</template>

<script>
import BarChart from "../charts/BarChart.vue";
import RadarChart from "../charts/RadarChart.vue";

export default {
  name: "SurveyAnalysis",
  components: {
    BarChart,
    RadarChart,
  },
  data() {
    return {
      datacollection: null,
      count: 25,
      widthTemp: "30px",
    };
  },
  mounted() {
    let barChartDiv = document.getElementsByClassName("bar-chart-div")[0];
    barChartDiv.style.width = 1000;
  },
  created() {
    this.widthTemp = this.count * 100 + "px";
  },
};
</script>

<style>
/* #bar-chart {
  height: 100% !important;
  width: 100% !important;
} */
</style>

<style scoped>
.chart-div div {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}

.bar-chart-div div {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 3000px;
}
.chart-container {
  overflow: scroll;
  overflow-x: hidden;
  height: 100%;
}

.chart-container::-webkit-scrollbar {
  width: 1vh;
}
.chart-container::-webkit-scrollbar-track {
  background-color: #dde0e7;
}

.chart-container::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #9cbbff;
}

.chart-div {
  display: flex;
  width: 100%;
  height: 90%;
  padding: 3%;
  justify-content: center;
  align-items: center;
  /* overflow: scroll; */
}

.bar-chart-div {
  display: flex;
  /* width: 3000px; */
  height: 90%;
  padding: 3%;
  justify-content: center;
  align-items: center;
  /* overflow: scroll; */
}

.chart-title {
  margin-top: 2%;
  margin-left: 2%;
  height: 10%;
}

.bar-chart-div-parent {
  display: flex;
  height: 100%;
  width: 100%;
  justify-content: flex-start;
  /* overflow: scroll; */
}

.bar-chart-container {
  overflow: scroll;
  overflow-y: hidden;
}
</style>
