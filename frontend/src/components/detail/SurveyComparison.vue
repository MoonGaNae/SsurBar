<template>
  <div v-if="!isDataExist" class="chart-container">
    <div class="select-div">
      <div class="select-div-text">비교 설문 선택</div>
      <el-select
        v-model="value"
        multiple
        :multiple-limit="5"
        @change="selectSurvey"
        placeholder="Select"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          :disabled="item.disabled"
        >
        </el-option>
      </el-select>
    </div>
    <div
      class="line-chart-div"
      :class="{ 'line-chart-div-flex-start': isFlexStart }"
    >
      <div>
        <LineChart :style="{ width: widthTemp }"></LineChart>
      </div>
    </div>
  </div>
  <div v-else class="empty-div">
    <EmptyData></EmptyData>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
import LineChart from "@/components/charts/LineChart";
import axios from "@/utils/axios.js";
import EmptyData from "@/components/detail/DataEmpty.vue";

export default {
  name: "SurveyComparison",
  components: { LineChart, EmptyData },
  props: ["surveyId"],
  data() {
    return {
      count: null,
      widthTemp: "",
      isFlexStart: false,
      isDataExist: false,
      value: [],
      isSelectFull: false,
      myDataSet: null,
      options: [],
      colorList: [
        `rgba(166,167,235,0.6)`,
        `rgba(176,147,215,0.6)`,
        `rgba(186,127,195,0.6)`,
        `rgba(196,107,175,0.6)`,
        `rgba(206,807,155,0.6)`,
      ],
    };
  },
  computed: {
    ...mapState("analysis", ["answerDataList", "questionCount"]),
  },
  watch: {
    answerDataList() {
      this.count = this.questionCount;

      this.widthTemp = this.count * 6 + "vw";
      if (this.count * 6 > 60) {
        this.isFlexStart = true;
      }

      this.makeChart();
    },
    widthTemp() {
      if (this.widthTemp != "") this.isDataExist = true;
    },
  },
  methods: {
    ...mapActions("analysis", ["setComparisonDataSets", "setComparisonLabels"]),
    ...mapGetters("analysis", ["getAnswerDataList", "getComparisonDataSets"]),
    makeChart() {
      let questionDataList = [];
      let questionTitles = [];

      let color = `rgba(156,187,255,0.6)`;

      this.getAnswerDataList().forEach((category) => {
        category.questionDataList.forEach((el) => {
          questionDataList.push(el.averageScore);

          questionTitles.push(el.number + " " + el.title);
        });
      });
      let dataSet = {
        label: "현재 설문",
        pointBackgroundColor: color,
        backgroundColor: color,
        borderWidth: 2,
        borderColor: color,
        fill: false,
        pointBorderColor: color,
        data: questionDataList,
      };
      let dataSets = [];

      dataSets.push(dataSet);

      this.myDataSet = dataSet;

      this.setComparisonDataSets(dataSets);
      this.setComparisonLabels(questionTitles);
    },
    getSameTemplateSurvey() {
      axios.get(`/survey/${this.surveyId}/template`).then((res) => {
        res.data.surveyInfoList.forEach((el, idx) => {
          let endTime = el.endTime;
          let id = el.surveyId;
          let teamName = el.teamName;
          // let title = el.title;
          let option = {
            id: id,
            value: idx,
            label: `${teamName} ${endTime}`,
            disabled: false,
          };
          this.options.push(option);
        });
      });
    },
    selectSurvey() {
      let dataSets = [];
      dataSets.push(this.myDataSet);
      this.value.forEach((optionIdx, idx) => {
        axios
          .get(`/survey/${this.options[optionIdx].id}/answer`, {
            params: { filterDataStr: encodeURI("[]") },
          })
          .then((res) => {
            let color = this.colorList[idx];

            let dataList = [];

            res.data.answerDataList.forEach((answerData) => {
              answerData.questionDataList.forEach((el) => {
                dataList.push(el.averageScore);
              });
            });

            let dataSet = {
              label: `${this.options[optionIdx].label}`,
              pointBackgroundColor: color,
              backgroundColor: color,
              borderWidth: 2,
              borderColor: color,
              fill: false,
              pointBorderColor: color,
              data: dataList,
            };

            dataSets.push(dataSet);
          });
      });

      this.setComparisonDataSets(dataSets);
    },
  },
  mounted() {
    this.getSameTemplateSurvey();
  },
  created() {
    this.count = this.questionCount;

    console.log(this.count);

    if (this.count != null) {
      this.widthTemp = this.count * 6 + "vw";
      if (this.count * 6 > 60) {
        this.isFlexStart = true;
      }
    }

    this.makeChart();
  },
};
</script>

<style scoped>
.chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  width: 100%;
}
.empty-div {
  height: 100%;
}
.line-chart-div {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80%;
  margin-left: 5%;
  margin-right: 5%;
  margin-top: 3vh;
  width: 90%;
  overflow: auto;
  overflow-y: hidden;
}

.line-chart-div > div {
  height: 100%;
  padding-top: 3%;
}

.line-chart-div > div > div {
  height: 90%;
}

.line-chart-div-flex-start {
  justify-content: flex-start !important;
}

.select-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 5%;
  padding-right: 5%;
  padding-top: 2vh;
  width: 100%;
}

.select-div div {
  width: 100%;
}

.el-select {
  width: 150% !important;
}

.select-div-text {
  width: 20% !important;
}

.line-chart-div::-webkit-scrollbar {
  height: 1vh;
}
.line-chart-div::-webkit-scrollbar-track {
  background-color: #dde0e7;
}

.line-chart-div::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #9cbbff;
}
</style>
