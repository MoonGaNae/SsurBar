<template>
  <div class="chart-container">
    <div class="select-div">
      <el-select v-model="value" multiple placeholder="Select">
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
    <div class="line-chart-div">
      <LineChart></LineChart>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
import LineChart from "@/components/charts/LineChart";

export default {
  name: "SurveyComparison",
  components: { LineChart },
  data() {
    return {
      count: null,
      widthTemp: "",
      isFlexCenter: false,
      isDataExist: false,
      value: [],
      options: [
        { value: 1, label: "1", disabled: false },
        { value: 2, label: "2", disabled: false },
        { value: 3, label: "1", disabled: false },
        { value: 4, label: "2", disabled: false },
        { value: 5, label: "1", disabled: false },
        { value: 6, label: "2", disabled: false },
        { value: 7, label: "1", disabled: false },
        { value: 8, label: "2", disabled: false },
        { value: 9, label: "1", disabled: false },
        { value: 10, label: "2", disabled: false },
      ],
    };
  },
  computed: {
    ...mapState("analysis", ["answerDataList"]),
  },
  watch: {
    answerDataList() {
      this.count = this.questionCount;

      this.widthTemp = this.count * 5 + "vh";
      if (this.count * 5 < 125) {
        this.isFlexCenter = true;
      }

      this.makeChart();
    },
    widthTemp() {
      console.log(this.widthTemp);
      if (this.widthTemp != "") this.isDataExist = true;
    },
  },
  methods: {
    ...mapActions("analysis", ["setComparisonDataSets", "setComparisonLabels"]),
    ...mapGetters("analysis", ["getAnswerDataList"]),
    makeChart() {
      let categoryCount = this.getAnswerDataList().length;
      let questionDataList = [];
      let questionTitles = [];
      let backgroundColorList = [];
      questionDataList.push([]);
      for (let i = 0; i < categoryCount; i++) {
        let r = 156;
        let g = 187;
        let b = 255;
        backgroundColorList.push(
          `rgba(${r + i * 10},${g - i * 20},${b - i * 20},0.6)`
        );
      }
      this.getAnswerDataList().forEach((category) => {
        category.questionDataList.forEach((el) => {
          questionDataList[0].push(el.averageScore);

          questionTitles.push(el.number + " " + el.title);
        });
      });
      let dataSets = [];
      for (let i = 0; i < categoryCount; i++) {
        let dataSet = {
          label: 1,
          pointBackgroundColor: backgroundColorList[i],
          backgroundColor: backgroundColorList[i],
          borderWidth: 2,
          borderColor: backgroundColorList[i],
          fill: false,
          pointBorderColor: backgroundColorList[i],
          data: questionDataList[i],
        };
        dataSets.push(dataSet);
      }
      this.setComparisonDataSets(dataSets);
      console.log(dataSets);
      this.setComparisonLabels(questionTitles);
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
.chart-container {
  display: flex;
  flex-direction: column;
  /* overflow: scroll;
  overflow-y: hidden; */
  align-items: center;
  height: 100%;
  width: 100%;
}

.line-chart-div {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80%;
  margin-left: 5%;
  margin-right: 5%;
  width: 90%;
  overflow: auto;
  overflow-y: hidden;
}
/*
.line-chart-div div {
  height: 50vh;
  width: 300px;
} */

.select-div {
  padding: 1vh;
  width: 100%;
}
</style>
