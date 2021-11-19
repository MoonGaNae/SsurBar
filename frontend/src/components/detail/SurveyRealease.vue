<template>
  <div class="release-container">
    <div v-if="isLoading"></div>
    <div v-else class="release-content">
      <div class="survey-info-div">
        <h1 v-text="title"></h1>
        <div class="team-name" v-text="teamName"></div>
        <h5 class="survey-date">
          <span v-text="startDate"></span> ~ <span v-text="endDate"></span>
        </h5>
        <p class="survey-description" v-text="description"></p>
      </div>
      <div class="link-list-div">
        <div class="response-link-div">
          <div class="link-type">응답 링크</div>
          <div class="blank"></div>
          <div class="link-url el-card is-always-shadow">
            <div>
              <i class="el-icon-link"></i>
              <input disabled type="text" v-model="responseUrl" />
            </div>
            <button
              class="yellow-button rounded-corner-button"
              @click="copyUrl(responseUrl)"
            >
              copy
            </button>
          </div>
        </div>
        <div class="result-link-div">
          <div class="link-type">결과 링크</div>
          <div class="blank"></div>
          <div class="link-url el-card is-always-shadow">
            <div>
              <i class="el-icon-link"></i>
              <input disabled type="text" v-model="resultUrl" />
            </div>
            <button
              class="yellow-button rounded-corner-button"
              @click="copyUrl(resultUrl)"
            >
              copy
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
export default {
  name: "Surveyreleasee",
  props: ["surveyId"],
  data() {
    return {
      responseUrl: "",
      resultUrl: "",
      endDate: null,
      startDate: null,
      teamName: "",
      title: "",
      description: "",
      isLoading: true,
    };
  },
  methods: {
    copyUrl(url) {
      let tmpTextarea = document.createElement("textarea");

      tmpTextarea.value = url;

      tmpTextarea.setAttribute("readonly", "");
      tmpTextarea.setAttribute("display", "none");
      document.body.appendChild(tmpTextarea);

      tmpTextarea.select();

      let successCopy = document.execCommand("copy");

      document.body.removeChild(tmpTextarea);

      console.log(successCopy);
    },
  },
  created() {
    axios
      .get(`/survey/${this.surveyId}/info`)
      .then((res) => {
        console.log(res);
        this.responseUrl = this.$responseUrl + res.data.responseUrl;
        this.resultUrl = this.$resultUrl + res.data.resultUrl;
        this.endDate = res.data.endTime;
        this.startDate = res.data.creationTime;
        this.teamName = res.data.teamName;

        axios
          .get(`/template/${res.data.templateId}/info`)
          .then((templateRes) => {
            this.title = templateRes.data.title;
            this.description = templateRes.data.description;
            this.isLoading = false;
          })
          .catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
.release-container {
  display: flex;
  align-items: center;
  height: 100%;
}

.release-content {
  width: 100%;
  height: 100%;
}
</style>

<style scoped>
.survey-info-div {
  margin: 5%;
}

.team-name {
  font-weight: 500;
  font-size: 200%;
}

.survey-title {
  font-weight: 600;
  font-size: 300%;
}

.survey-duration {
  color: #878a91;
}

.survey-description {
  margin-top: 2%;
  font-size: 100%;
}

.link-list-div {
  padding-top: 2vh;
  padding-bottom: 5vh;
}
</style>
