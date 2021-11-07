<template>
  <div class="chart-container">
    <div
      class="el-card box-card is-always-shadow radar-chart-container"
      style="width: 90%; margin-left: 5%; margin-top: 3%; margin-bottom: 2%"
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
      style="width: 90%; margin-left: 5%; margin-top: 2%; margin-bottom: 2%"
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
    <div class="temp-container">
      <div title="최고 평균">
        <ul class="data-ul">
          <li v-for="(data, idx) in highestAverageList" :key="idx">
            <div class="data-title-div">
              <div class="question-number">{{ data.number }}</div>
              <div>{{ data.title }}</div>
            </div>
            <div class="score-number">{{ data.averageScore }}</div>
          </li>
        </ul>
      </div>
      <div title="최저 편차">
        <ul class="data-ul">
          <li v-for="(data, idx) in lowestStandardDeviationList" :key="idx">
            <div class="data-title-div">
              <div class="question-number">{{ data.number }}</div>
              <div>{{ data.title }}</div>
            </div>
            <div class="score-number">{{ data.standardDeviation }}</div>
          </li>
        </ul>
      </div>
      <div title="최저 평균">
        <ul class="data-ul">
          <li v-for="(data, idx) in lowestAverageList" :key="idx">
            <div class="data-title-div">
              <div class="question-number">{{ data.number }}</div>
              <div>{{ data.title }}</div>
            </div>
            <div class="score-number">{{ data.averageScore }}</div>
          </li>
        </ul>
      </div>
      <div title="최고 편차">
        <ul class="data-ul">
          <li v-for="(data, idx) in highestStandardDeviationList" :key="idx">
            <div class="data-title-div">
              <div class="question-number">{{ data.number }}</div>
              <div>{{ data.title }}</div>
            </div>
            <div class="score-number">{{ data.standardDeviation }}</div>
          </li>
        </ul>
      </div>
      <el-collapse>
        <el-collapse-item title="최저 편차" name="2">
          <div>
            Operation feedback: enable the users to clearly perceive their operations by style
            updates and interactive effects;
          </div>
          <div>
            Visual feedback: reflect current state by updating or rearranging elements of the page.
          </div>
        </el-collapse-item>
        <el-collapse-item title="최저평균" name="3">
          <div>Simplify the process: keep operating process simple and intuitive;</div>
          <div>
            Definite and clear: enunciate your intentions clearly so that the users can quickly
            understand and make decisions; Definite and clear: enunciate your intentions clearly so
            that the users can quickly understand and make decisions; Definite and clear: enunciate
            your intentions clearly so that the users can quickly understand and make decisions;
            Definite and clear: enunciate your intentions clearly so that the users can quickly
            understand and make decisions; Definite and clear: enunciate your intentions clearly so
            that the users can quickly understand and make decisions;
          </div>
          <div>
            Easy to identify: the interface should be straightforward, which helps the users to
            identify and frees them from memorizing and recalling.
          </div>
        </el-collapse-item>
        <el-collapse-item title="최고 편차" name="4">
          <div>
            Decision making: giving advices about operations is acceptable, but do not make
            decisions for the users;
          </div>
          <div>
            Controlled consequences: users should be granted the freedom to operate, including
            canceling, aborting or terminating current operation.
          </div>
        </el-collapse-item>
      </el-collapse>
      <div class="temp-div"></div>
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
import { mapState } from "vuex";

export default {
  name: "SurveyAnalysis",
  components: {
    BarChart,
    RadarChart,
  },
  data() {
    return {
      dataCollection: null,
      count: 14,
      widthTemp: "",
      testTitle: "testest",
      // highestAverageList: null,
      // highestStandardDeviationList: null,
      // lowestAverageList: null,
      // lowestStandardDeviationList: null,
    };
  },
  computed: {
    ...mapState("analysis", [
      "answerDataList",
      "highestAverageList",
      "highestStandardDeviationList",
      "lowestAverageList",
      "lowestStandardDeviationList",
    ]),
  },
  mounted() {},
  created() {
    this.widthTemp = this.count * 5 + "%";
  },
};
</script>

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
  justify-content: flex-start;
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
  height: 70vh;
}

.bar-chart-container::-webkit-scrollbar {
  width: 1vh;
}
.bar-chart-container::-webkit-scrollbar-track {
  background-color: #dde0e7;
}

.bar-chart-container::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #9cbbff;
}

.radar-chart-container {
  height: 70vh;
}

.data-title-div {
  display: flex;
  justify-content: space-between;
}

.question-number {
  width: 3vh;
  margin-right: 1vh;
  text-align: center;
}

.data-ul li {
  display: flex;
  justify-content: space-between;
}

.data-ul li div {
  text-align: center;
}

.score-number {
  width: 10vh;
}
</style>
