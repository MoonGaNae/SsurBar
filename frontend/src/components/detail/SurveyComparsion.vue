<template>
  <div class="chart-container">
    <div class="radar-chart-container">
      <div class="chart-title">
        <h2>카테고리별 데이터</h2>
      </div>
      <div class="chart-div">
        <RadarChart />
      </div>
    </div>
    <div class="bar-chart-container">
      <div class="chart-title">
        <h2>문항별 데이터</h2>
      </div>
      <div
        v-if="isBarDataExist"
        class="bar-chart-div-parent"
        :class="{ 'bar-chart-div-parent-center': isFlexCenter }"
      >
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
              <div class="summary-question-number">{{ data.number }}</div>
              <div class="summary-question-title">{{ data.title }}</div>
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
              <div class="summary-question-number">{{ data.number }}</div>
              <div class="summary-question-title">{{ data.title }}</div>
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
              <div class="summary-question-number">{{ data.number }}</div>
              <div class="summary-question-title">{{ data.title }}</div>
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
              <div class="summary-question-number">{{ data.number }}</div>
              <div class="summary-question-title">{{ data.title }}</div>
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
            class="question-div el-card is-always-shadow"
            v-for="(
              questionData, questionDataIdx
            ) in answerData.questionDataList"
            :key="questionDataIdx"
          >
            <div>
              <span class="question-number">Q{{ questionData.number }}.</span
              ><span class="question-title">{{ questionData.title }}</span>
            </div>
            <div
              class="progress-div"
              v-for="(
                questionAnswer, questionAnswerIdx
              ) in questionData.questionAnswerDtoList"
              :key="questionAnswerIdx"
            >
              <div class="progress-bar-base">
                <div
                  class="progress-bar-color"
                  :style="{ width: questionAnswer.percentage + '%' }"
                >
                  <div>{{ questionAnswer.sentence }}</div>
                  <div>{{ questionAnswer.percentage }} %</div>
                </div>
              </div>
              <div>{{ questionAnswer.count }} 명</div>
            </div>
          </div>
        </el-collapse-item>
      </el-collapse>
      <div class="temp-div"></div>
    </div>
  </div>
</template>

<script>
import BarChart from "../charts/BarChart.vue";
import RadarChart from "../charts/RadarChart.vue";
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: "SurveyAnalysis",
  components: {
    BarChart,
    RadarChart,
  },
  data() {
    return {
      dataCollection: null,
      count: null,
      widthTemp: "",
      testTitle: "testest",
      isFlexCenter: false,
      isBarDataExist: false,
    };
  },
  computed: {
    ...mapState("analysis", [
      "answerDataList",
      "highestAverageList",
      "highestStandardDeviationList",
      "lowestAverageList",
      "lowestStandardDeviationList",
      "questionCount",
    ]),
  },
  watch: {
    answerDataList() {
      this.count = this.questionCount;

      console.log(this.count);

      this.widthTemp = this.count * 5 + "vh";
      if (this.count * 5 < 125) {
        this.isFlexCenter = true;
      }

      this.makeChart();
    },
    widthTemp() {
      console.log(this.widthTemp);
      if (this.widthTemp != "") this.isBarDataExist = true;
    },
  },
  methods: {
    ...mapActions("analysis", [
      "setRadarDataSets",
      "setRadarLabels",
      "setBarDataSets",
      "setBarLabels",
    ]),
    ...mapGetters("analysis", [
      "getAnswerDataList",
      "getRadarLabels",
      "getRadarDataSets",
    ]),
    makeChart() {
      /* 방사형 그래프 데이터 처리 */
      let averageDataList = [];
      let dataLabels = [];

      if (this.getAnswerDataList() == null) return;

      this.getAnswerDataList().forEach((el) => {
        averageDataList.push(el.averageScore);
        dataLabels.push(el.categoryName);
      });

      averageDataList.push(1);
      dataLabels.push("test");

      let dataSets = [];
      let dataSet = {
        label: "Dataset",
        pointBackgroundColor: "white",
        backgroundColor: "rgba(156,187,255,0.4)",
        borderWidth: 2,
        pointBorderColor: "#9cbbff",
        data: averageDataList,
      };

      dataSets.push(dataSet);

      this.setRadarDataSets(dataSets);
      this.setRadarLabels(dataLabels);

      /* 막대 그래프 데이터 처리 */
      let questionDataList = [];
      let questionTitles = [];
      let backgroundColorList = [];
      let categoryCount = this.getAnswerDataList().length;
      let categoryNameList = [];

      for (let i = 0; i < categoryCount; i++) {
        questionDataList.push([]);
        let r = 156;
        let g = 187;
        let b = 255;
        backgroundColorList.push(
          `rgba(${r + i * 10},${g - i * 20},${b - i * 20},0.6)`
        );
      }

      this.getAnswerDataList().forEach((category, idx) => {
        categoryNameList.push(category.categoryName);
        category.questionDataList.forEach((el) => {
          for (let i = 0; i < categoryCount; i++) {
            if (i == idx) {
              questionDataList[i].push(el.averageScore);
            } else {
              questionDataList[i].push(null);
            }
          }
          questionTitles.push(el.number + " " + el.title);
        });
      });

      let barDataSets = [];

      for (let i = 0; i < categoryCount; i++) {
        let barDataSet = {
          label: categoryNameList[i],
          pointBackgroundColor: "white",
          backgroundColor: backgroundColorList[i],
          borderWidth: 2,
          pointBorderColor: "#9cbbff",
          data: questionDataList[i],
        };
        barDataSets.push(barDataSet);
      }

      this.setBarDataSets(barDataSets);
      this.setBarLabels(questionTitles);
    },
  },
  created() {
    this.count = this.questionCount;

    if (this.count != null) {
      this.widthTemp = this.count * 5 + "vh";
      if (this.count * 5 < 125) {
        this.isFlexCenter = true;
      }
    }
    this.makeChart();
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

.chart-title {
  margin-top: 2%;
  margin-left: 2%;
  height: 10%;
}

.data-title-div {
  display: flex;
  justify-content: space-between;
}

.question-number {
  font-size: 200%;
  width: 3vh;
  margin-right: 1vh;
  font-weight: 600;
  text-align: center;
}

.score-number {
  width: 10vh;
}

.temp-container {
  margin-top: 2vh;
  border-bottom: 0px none !important;
  /* height: 70%; */
}

.category-list {
  margin: 3vh;
}

.question-div {
  margin-bottom: 1vh;
  padding: 3%;
  /* box-shadow: #dde0e7; */
}

.progress-bar-base {
  background-color: #dde0e7;
  border-radius: 10px;
  width: 95%;
}

.question-div > div {
  margin-bottom: 1vh;
}

.question-title {
  font-size: 150%;
}

.summary-question-number {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.5vh 1vh 0.5vh 2vh;
  /* padding-left: 1vh; */
  font-size: 150%;
  width: 3vh;
  margin-right: 1vh;
  font-weight: 400;
}

.summary-question-title {
  padding-left: 1vh;
  display: flex;
  align-items: center;
}

.progress-div {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vh;
}

.progress-bar-color {
  padding-left: 1vh;
  padding-right: 1vh;
  color: #747577;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #9cbbff;
  /* width: 80%; */
  border-radius: 10px;
  height: 100%;
}
</style>
