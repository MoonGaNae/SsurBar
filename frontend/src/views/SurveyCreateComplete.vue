<template>
  <div id="wrapper">
    <div style="background-color: rgb(5, 25, 58); height: 100vh">
      <div>1</div>
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
        <div class="container">
          <div class="complete-message-div">
            <div class="complete-message1">설문 생성이 완료되었습니다</div>
            <div class="complete-message2">링크를 통해 설문을 공유해보세요</div>
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
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "@/utils/axios.js";

export default {
  name: "SurveyCreateComplete",
  data() {
    return {
      responseUrl: "testRes",
      resultUrl: "testResult",
    };
  },
  methods: {
    ...mapGetters("survey", ["getSurveyId"]),
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
      .get(`/survey/` + this.getSurveyId())
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
@import "../assets/style/buttons.css";
.complete-message-div {
  /* display: flex; */
  justify-content: center;
  height: 30vh;
  align-items: flex-end;
}

.complete-message1 {
  display: flex;
  font-size: 3vh;
  justify-content: center;
  align-items: flex-end;
  height: 85%;
}

.complete-message2 {
  display: flex;
  font-size: 3vh;
  justify-content: center;
  align-items: flex-start;
  height: 15%;
}

.response-link-div {
  margin-top: 10vh;
  margin-bottom: 1.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.result-link-div {
  height: 5vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.link-type {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  font-size: 120%;
}

.link-url {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 77%;
}

.link-url div {
  margin-left: 1.5%;
  width: 100%;
  display: flex;
  align-items: center;
}

.link-url input {
  width: 100%;
  border-style: none;
  background: none;
}

.blank {
  width: 3%;
}

.rounded-corner-button {
  margin: 1%;
}

.el-icon-link {
  font-size: 150%;
  margin-right: 1%;
}
</style>
