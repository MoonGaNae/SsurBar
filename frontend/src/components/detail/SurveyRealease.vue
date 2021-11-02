<template>
  <div class="realease-container">
    <div class="realeas-content">
      <div class="link-list-div">
        <div class="response-link-div">
          <div class="link-type">응답 링크</div>
          <div class="blank"></div>
          <div class="link-url el-card is-always-shadow">
            <div>
              <i class="el-icon-link"></i>
              <input disabled type="text" v-model="responseUrl" />
            </div>
            <button class="yellow-button rounded-corner-button" @click="copyUrl(responseUrl)">
              copy
            </button>
          </div>
        </div>
        <div class="result-link-div">
          <div class="link-type">결과 링크</div>
          <div class="blank"></div>
          <div class="link-url el-card is-always-shadow">
            <div>
              <i class="el-icon-link"></i> <input disabled type="text" v-model="resultUrl" />
            </div>
            <button class="yellow-button rounded-corner-button" @click="copyUrl(resultUrl)">
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
  name: "SurveyRealease",
  props: ["surveyId"],
  data() {
    return {
      responseUrl: "",
      resultUrl: "",
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
  mounted() {
    axios
      .get(`/survey/` + this.surveyId)
      .then((res) => {
        console.log(res);
        this.responseUrl = res.data.responseUrl;
        this.resultUrl = res.data.resultUrl;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style>
.realease-container {
  display: flex;
  align-items: center;
  height: 100%;
}

.realeas-content {
  width: 100%;
}
</style>
