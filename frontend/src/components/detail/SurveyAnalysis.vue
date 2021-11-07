<template>
  <div class="chart-container">
    <div
      class="radar-chart-container"
      style="width: 100%; padding-left: 2%; margin-top: 3%; margin-bottom: 2%"
    >
      <div class="chart-title">
        <h2>카테고리별 데이터</h2>
      </div>
      <div class="chart-div">
        <RadarChart />
      </div>
    </div>
    <div
      class="bar-chart-container"
      style="width: 100%; padding-left: 2%; margin-top: 2%; margin-bottom: 2%"
    >
      <div class="chart-title">
        <h2>문항별 데이터</h2>
      </div>
      <div class="bar-chart-div-parent">
        <div class="bar-chart-div">
          <BarChart :style="{ width: widthTemp }" />
        </div>
      </div>
    </div>
    <div class="temp-container">
      <div class="data-div">
        <div class="data-title">최고 평균</div>
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
      <div class="data-div">
        <div class="data-title">최저 편차</div>
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
      <div class="data-div">
        <div class="data-title">최저 평균</div>
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
      <div class="data-div">
        <div class="data-title">최고 편차</div>
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
      <el-collapse class="category-list">
        <el-collapse-item
          v-for="(answerData, answerDataIdx) in answerDataList"
          :title="answerData.categoryName"
          :key="answerDataIdx"
          :name="answerDataIdx"
        >
          <div
            v-for="(questionData, questionDataIdx) in answerData.questionDataList"
            :key="questionDataIdx"
          >
            {{ questionData }}
            <div>{{ questionData.number }}</div>
            <div>{{ questionData.title }}</div>
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
    this.widthTemp = this.count * 5 + "vh";
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
  /* width: 3000px; */
}
.chart-container {
  overflow: scroll;
  overflow-x: hidden;
  height: 100%;
}

.chart-container > div {
  border-bottom: 1px solid #dde0e7;
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
  height: 90%;
  padding-bottom: 2vh;
  padding-top: 2vh;
  justify-content: flex-start;
  align-items: center;
}

.chart-title {
  margin-top: 2%;
  margin-left: 2%;
  height: 10%;
}

.bar-chart-div-parent {
  display: flex;
  /* height: 100%; */
  width: 96%;
  justify-content: flex-start;
  overflow: scroll;
  overflow-y: hidden;
}

.bar-chart-container {
  /* overflow: scroll;
  overflow-y: hidden; */
  display: inline-block;
  height: 70vh;
  margin-bottom: 3vh;
}

.bar-chart-div-parent::-webkit-scrollbar {
  height: 1vh;
}
.bar-chart-div-parent::-webkit-scrollbar-track {
  border-radius: 8px;
  background-color: #dde0e7;
}

.bar-chart-div-parent::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #9cbbff;
}

/* .bar-chart-container::-webkit-scrollbar {
  width: 1vh;
}
.bar-chart-container::-webkit-scrollbar-track {
  background-color: #dde0e7;
}

.bar-chart-container::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #9cbbff;
} */

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
  font-weight: 600;
  text-align: center;
}

.data-ul {
  padding: 0;
}

.data-ul li {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #dde0e7;
}
.data-ul li:hover {
  background-color: #dde0e7;
}

.data-ul li div {
  text-align: center;
}

.score-number {
  width: 10vh;
}

.data-title {
  padding-left: 1vh;
  background-color: #9cbbff;
}

.temp-container {
  margin-top: 2vh;
  border-bottom: 0px none !important;
  /* height: 70%; */
}

.data-div {
  /* border-bottom: 1px solid; */
  padding-left: 3vh;
  padding-right: 3vh;
}

.category-list {
  margin: 3vh;
}
</style>
