<template>
  <div id="wrapper">
    <div style="background-color: rgb(5, 25, 58); height: 100vh">
      <div>네브바같은 느낌으로다가</div>
      <div
        style="
          background-position: center;
          background-color: white;
          margin-top: 3%;
          margin-left: 4%;
          margin-right: 4%;
          height: 90vh;
          border-radius: 60px 60px 0% 0%;
        "
      >
        <div class="main-container">
          <!-- <div class="filter-container" v-if="!checkFullContent">sdadasdsad</div>
          <div class="content-container" :class="{ isFullContent: checkFullContent }"> -->
          <div id="tab-div">
            <ul class="tabs">
              <li class="tab" :class="{ selectedTab: selectedTabNum == 0 }" @click="changeTab(0)">
                분석
              </li>
              <!-- <li class="tab">비교</li> -->
              <li class="tab" :class="{ selectedTab: selectedTabNum == 2 }" @click="changeTab(2)">
                배포
              </li>
              <li class="tab" :class="{ selectedTab: selectedTabNum == 3 }" @click="changeTab(3)">
                결과
              </li>
            </ul>
          </div>
          <div class="detail-container">
            <div class="filter-container el-card is-always-shadow" v-if="!checkFullContent">
              sdadasdsad
            </div>
            <div
              class="content-container el-card is-always-shadow"
              :class="{ isFullContent: checkFullContent }"
            >
              <div class="component-div" v-if="selectedTabNum == 0">
                <SurveyAnalysis :surveyId="surveyId" />
              </div>
              <div class="component-div" v-if="selectedTabNum == 2">
                <SurveyRealease :surveyId="surveyId" />
              </div>
              <div class="component-div" v-if="selectedTabNum == 3">
                <SurveyResult :surveyId="surveyId" />
              </div>
            </div>
            <div class="feedback-container el-card is-always-shadow" v-if="!checkFullContent">
              12321323
            </div>
          </div>
          <!-- </div>
          <div class="feedback-container" v-if="!checkFullContent">12321323</div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from "@/utils/axios.js";
// import { mapActions, mapGetters } from "vuex";
import SurveyRealease from "@/components/detail/SurveyRealease.vue";
import SurveyAnalysis from "@/components/detail/SurveyAnalysis.vue";
import SurveyResult from "@/components/detail/SurveyResult.vue";

export default {
  name: "SurveyDetail",
  components: {
    SurveyRealease,
    SurveyAnalysis,
    SurveyResult,
  },
  data() {
    return {
      selectedTabNum: 0,
      surveyId: "samplesurvey1",
    };
  },
  methods: {
    changeTab(tabNum) {
      this.selectedTabNum = tabNum;
    },
  },
  computed: {
    checkFullContent() {
      if (this.selectedTabNum == 0 || this.selectedTabNum == 1) {
        return false;
      }
      return true;
    },
  },
};
</script>

<style>
.component-div {
  height: 80%;
}

ul.tabs {
  margin: 0px;
  padding: 0px;
  list-style: none;
}

ul.tabs li {
  background: none;
  color: #dde0e7;
  display: inline-block;
  padding: 10px 15px;
  cursor: pointer;
  /* background-color: #dde0e7; */
}

ul.tabs li:hover {
  background: none;
  color: #9cbbff;
  display: inline-block;
  padding: 10px 15px;
  cursor: pointer;
}

.main-container {
  padding: 3%;
  width: 100%;
  /* display: flex;
  justify-content: space-between; */
}

.detail-container {
  min-height: 60vh;
  display: flex;
  justify-content: space-between;
}

.feedback-container {
  width: 10%;
  /* border-style: solid; */
}
.content-container {
  width: 78%;
  /* border-style: solid; */
}
.filter-container {
  width: 10%;
  /* border-style: solid; */
}

.isFullContent {
  width: 100%;
}

.selectedTab {
  color: #0e4194 !important;
  /* font-size: 120%; */
  font-weight: 700;
  /* background-color: white !important; */
  /* border-style: solid;
  border-color: #0e4194 !important;
  border-width: 2px !important;
  border-bottom: 0; */
}

.tab {
  /* border-style: solid; */
  /* border-width: 1px;
  border-color: #dde0e7;
  border-bottom: 0; */
}

.teste {
  /* border-style: solid; */
}

#tab-div {
  /* border-style: solid;
  border-width: 0px 0px 1px 0px; */
  margin-bottom: 3%;
}

.el-card {
  transition: 0s !important;
}
</style>
