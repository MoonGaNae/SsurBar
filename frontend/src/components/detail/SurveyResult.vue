<template>
  <div>
    <div style="margin-left:3%; margin-top:3%">
      <h1 >설문 결과</h1>
      <br>
      <h3>싸피 만족도 조사</h3>
      <h4>2021.10.11 00:00 ~ 2021.10.22 23:59</h4>
      <hr style="width:97%">
    </div>
    <div>
      <div class="d-flex">
        <div style="margin-left:7%;margin-top:1%; margin-right:10%">
          <br>
          <h3 style="margin-left:2%;margin-bottom:6%">카테고리별 데이터</h3>
          <div class="chart-div">
            <RadarChart />
          </div>
        </div>
        <div class="chart-container">
       
          <div class="el-card box-card is-always-shadow" style="margin-bottom:1%">
            <h3>문항 1</h3>
            <HorizontalBarChart />
          </div>
          <div class="el-card box-card is-always-shadow">
            <h3>문항 2</h3>
            <HorizontalBarChart/>
          </div>
          <div class="el-card box-card is-always-shadow" style="margin-bottom:1%">
            <h3>문항 2</h3>
            <HorizontalBarChart/>
          </div>
          <div class="el-card box-card is-always-shadow">
            <h3>문항 2</h3>
            <HorizontalBarChart/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HorizontalBarChart from "../charts/HorizontalBarChart.vue";
import RadarChart from "../charts/RadarChart.vue";

export default {
  name: "SurveyResult",
  components: {
    HorizontalBarChart,
    RadarChart,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
.chart-div {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30%;
  width: 100%;
}

.chart-container {
  overflow: scroll;
  overflow-x: hidden;
  height: 120%;
  width: 55%;
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
</style>