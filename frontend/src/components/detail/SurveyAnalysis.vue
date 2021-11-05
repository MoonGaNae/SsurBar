<template>
  <div>
    <div class="el-card box-card is-always-shadow" style="width:90%; height:80%; margin-left:5%; margin-top:3%; margin-bottom:2%">
      <div style="margin-top: 2%; margin-left: 2%; ">
        <h2>카테고리별 데이터</h2>
      </div>     
      <RadarChart style="margin-left: 30%;margin-top: -5%; width:24%; height:24%"/> 
    </div>
    <div class="chartWrapper el-card box-card is-always-shadow" style="width:90%; height:80%; margin-left:5%; margin-top:2%; margin-bottom:2%">
      <div style="margin-top: 2%; margin-left: 2%">
        <h2>문항별 데이터</h2>
      </div>
      <div class="">
        <BarChart style="width:1500px; height:40%"/>

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
import BarChart from '../charts/BarChart.vue'
import RadarChart from '../charts/RadarChart.vue'

export default {
  name: "SurveyAnalysis",
  components: {
    BarChart,
    RadarChart,
  },
  data() {
    return {
      datacollection: null
    }
  },
};
</script>
<style scoped>
.chartWrapper {
  position: relative;
}

.chartWrapper > canvas {
  position: absolute;
  left: 0;
  top: 0;
  pointer-events: none;
}

.chartAreaWrapper {
  width: 15000px;
  overflow-x: scroll;
}
</style>