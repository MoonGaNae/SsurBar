<template>
  <div> 
    <BarChart/>
    <RadarChart/>
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
