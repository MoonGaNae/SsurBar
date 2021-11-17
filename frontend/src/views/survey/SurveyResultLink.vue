<template>
  <div>
    <div v-if="!isAnswerEmpty" class="chart-container">
      <div class="title-div">
        <h1 v-text="surveyTitle"></h1>
        <div class="team-name" v-text="teamName"></div>
        <h5 class="survey-date">
          <span v-text="creationTime"></span> ~ <span v-text="endTime"></span>
        </h5>
        <p class="survey-description" v-text="surveyDescription"></p>
      </div>
      <div class="radar-chart-container">
        <div class="chart-title">
          <h2><i class="fas fa-poll"></i> 카테고리별 데이터</h2>
        </div>
        <div class="chart-div">
          <RadarChart />
        </div>
      </div>
      <div class="bar-chart-container">
        <div class="chart-title">
          <h2><i class="fas fa-poll"></i> 문항별 데이터</h2>
        </div>
        <div class="bar-chart-div-parent" :class="{ 'bar-chart-div-parent-center': isFlexCenter }">
          <div class="bar-chart-div">
            <BarChart :style="{ width: `100%` }" />
          </div>
        </div>
      </div>
      <div class="summary-container">
        <h3 class="summary-title"><i class="far fa-thumbs-up"></i> 강점</h3>
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
        <h3 class="summary-title none-border"><i class="far fa-thumbs-down"></i> 약점</h3>
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
        <h3 class="summary-title"><i class="far fa-list-alt"></i> 문항별 응답</h3>
        <el-collapse class="category-list">
          <el-collapse-item
            v-for="(answerData, answerDataIdx) in answerDataList"
            :title="answerData.categoryName"
            :key="answerDataIdx"
            :name="answerDataIdx"
          >
            <div
              class="question-div el-card is-always-shadow"
              v-for="(questionData, questionDataIdx) in answerData.questionDataList"
              :key="questionDataIdx"
            >
              <div>
                <span class="question-number">Q{{ questionData.number }}.</span
                ><span class="question-title">{{ questionData.title }}</span>
              </div>
              <div
                class="progress-div"
                v-for="(questionAnswer, questionAnswerIdx) in questionData.questionAnswerDtoList"
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
        <h3 class="summary-title"><i class="far fa-comments"></i> 피드백</h3>
        <div class="feedback-div">{{ feedbackText }}</div>
      </div>
    </div>
    <div v-else class="empty-div">
      <div class="loading-div" v-if="isLoading" v-loading="true"></div>
      <EmptyData v-else></EmptyData>
    </div>
  </div>
</template>

<script>
import BarChart from "@/components/charts/BarChart.vue";
import RadarChart from "@/components/charts/RadarChart.vue";
import EmptyData from "@/components/detail/DataEmpty.vue";
import { mapState, mapGetters, mapActions } from "vuex";
import axios from "@/utils/axios.js";

export default {
  name: "SurveyAnalysis",
  components: {
    BarChart,
    RadarChart,
    EmptyData,
  },
  data() {
    return {
      count: null,
      widthTemp: "",
      surveyId: null,
      templateId: null,
      isFlexCenter: false,
      isBarDataExist: false,
      filterNameList: [],
      surveyTitle: null,
      surveyDescription: null,
      creationTime: null,
      endTime: null,
      teamName: null,
      isLoading: true,
      isAnswerEmpty: true,
      feedbackText: "피드백이 존재하지 않습니다",
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

      this.widthTemp = this.count * 5 + "vh";
      if (this.count * 5 < 125) {
        this.isFlexCenter = true;
      }

      this.isLoading = false;

      this.makeChart();
    },
    widthTemp() {
      console.log(this.widthTemp);
      if (this.widthTemp != "") this.isBarDataExist = true;
    },
  },
  methods: {
    ...mapActions("analysis", [
      "setAnswerData",
      "setRadarDataSets",
      "setRadarLabels",
      "setBarDataSets",
      "setBarLabels",
    ]),
    ...mapGetters("analysis", ["getAnswerDataList", "getRadarLabels", "getRadarDataSets"]),
    makeChart() {
      /* 방사형 그래프 데이터 처리 */
      let averageDataList = [];
      let dataLabels = [];

      console.log(this.getAnswerDataList().length);

      if (this.getAnswerDataList() == null || this.getAnswerDataList().length == 0) {
        this.isAnswerEmpty = true;

        return;
      }
      this.isAnswerEmpty = false;

      this.getAnswerDataList().forEach((el) => {
        averageDataList.push(el.averageScore);
        dataLabels.push(el.categoryName);
      });

      let count = averageDataList.length;

      for (let i = count; i < 3; i++) {
        averageDataList.push(0);
        dataLabels.push("");
      }

      let dataSets = [];
      let dataSet = {
        label: "Score",
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
      let backgroundColorList = [
        `rgba(10,20,30,0.6)`,
        `rgba(80,20,30,0.6)`,
        `rgba(10,200,30,0.6)`,
        `rgba(120,200,155,0.6)`,
        `rgba(160,20,30,0.6)`,
        `rgba(200,120,30,0.6)`,
        `rgba(40,20,155,0.6)`,
        `rgba(240,20,30,0.6)`,
        `rgba(10,120,30,0.6)`,
        `rgba(10,240,30,0.6)`,
        `rgba(100,120,30,0.6)`,
        `rgba(100,200,30,0.6)`,
        `rgba(100,20,130,0.6)`,
      ];
      let categoryCount = this.getAnswerDataList().length;
      let categoryNameList = [];

      for (let i = 0; i < categoryCount; i++) {
        questionDataList.push([]);
        let r = 156;
        let g = 187;
        let b = 255;
        backgroundColorList.push(`rgba(${r + i * 10},${g - i * 20},${b - i * 20},0.6)`);
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
          let title = el.number + " " + el.title;
          let length = title.length;
          let label = length > 10 ? title.substring(0, 10) + "..." : title;
          questionTitles.push(label);
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
    getTemplateInfo(templateId) {
      axios.get(`/template/${templateId}/info`).then((res) => {
        console.log(res.data);
        this.surveyTitle = res.data.title;
        this.surveyDescription = res.data.description;
      });
    },
    async getFilters(surveyId) {
      await axios.get(`/survey/${surveyId}/filters`).then((res) => {
        res.data.filterQuestionList.forEach((el) => {
          this.filterNameList.push(el.title);
        });
      });
    },
    getSurveyInfo(surveyId) {
      axios.get(`/survey/${surveyId}/info`).then((res) => {
        this.creationTime = res.data.creationTime;
        this.endTime = res.data.endTime;
        this.teamName = res.data.teamName;
      });
    },
    getFeedback(surveyId) {
      axios
        .get(`/feedback/${surveyId}`)
        .then((res) => {
          if (res.data.comment == null || res.data.comment == "") {
            this.feedbackText = "피드백이 존재하지 않습니다";
          } else {
            this.feedbackText = res.data.comment;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getIds() {
      const linkCode = this.$route.params.linkCode;

      this.count = this.questionCount;

      console.log(this.isLoading);

      axios
        .get(`/survey/decode-link`, {
          params: {
            linkCode: linkCode,
            type: "result",
          },
        })
        .then((res) => {
          this.surveyId = res.data.surveyId;
          this.templateId = res.data.templateId;

          this.getTemplateInfo(this.templateId);
          this.getFilters(this.surveyId);
          this.getSurveyInfo(this.surveyId);
          this.getFeedback(this.surveyId);

          this.setAnswerData({
            surveyId: this.surveyId,
            filterStr: "[]",
          });
        });
    },
  },
  created() {
    this.getIds();
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
  min-height: 100vh;
  height: 120%;
  width: 100%;
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
  /* overflow: scroll;
  overflow-x: hidden; */
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
  width: 100%;
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
}

.bar-chart-div-parent-center {
  justify-content: center !important;
}

.bar-chart-container {
  width: 100%;
  padding-left: 2%;
  margin-top: 2%;
  margin-bottom: 2%;
  /* overflow: scroll;
  overflow-y: hidden; */
  display: inline-block;
  height: 70vh;
  width: 100%;
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

.radar-chart-container {
  width: 100%;
  padding-left: 2%;
  margin-top: 3%;
  /* margin-bottom: 2%; */
  height: 70vh;
}

.title-div {
  padding: 3%;
  padding-bottom: 1.5%;
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

.data-ul {
  padding: 0;
}

.data-ul li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #dde0e7;
  border-radius: 4px;
}
.data-ul li:hover {
  background-color: rgba(156, 187, 255, 0.2);
}

.data-ul li div {
  text-align: center;
}

.score-number {
  width: 10vh;
}

.data-title {
  padding-left: 1vh;
  height: 4vh;
  border-radius: 4px;
  display: flex;
  align-items: center;
  background-color: #dde0e7;
}

.feedback-div {
  margin: 3vh;
  margin-bottom: 10vh;
}

.summary-container {
  margin-top: 2vh;
  margin-bottom: 2vh;
  border-bottom: 0px none !important;
}

.data-div {
  padding-top: 3vh;
  padding-left: 3vh;
  padding-right: 3vh;
}

.category-list {
  margin: 3vh;
}

.question-div {
  margin-bottom: 1vh;
  padding: 3%;
}

.progress-bar-base {
  background-color: #dde0e7;
  border-radius: 10px;
  width: 95%;
}

.survey-date {
  margin-bottom: 1vh;
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
.team-name {
  margin-bottom: 1vh;
}

.survey-description {
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
  border-radius: 10px;
  height: 100%;
}

.empty-div {
  display: flex;
  align-items: center;
  height: 90vh;
  justify-content: center;
}

.loading-div {
  height: 90vh;
}

.summary-title {
  padding-top: 5vh;
  border-top: 1px solid #dde0e7;
}

.none-border {
  border-top: 0px;
}
</style>
