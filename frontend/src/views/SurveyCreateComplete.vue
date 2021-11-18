<template>
  <div id="wrapper">
    <div class="main-container">
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
          </div>
          <button
            class="custom-btn2 btn-5"
            @click="copyUrl(responseUrl)"
          >
            copy
          </button>
        </div>
        <div class="result-link-div">
          <div class="link-type">결과 링크</div>
          <div class="blank"></div>
          <div class="link-url el-card is-always-shadow">
            <div>
              <i class="el-icon-link"></i>
              <input disabled type="text" v-model="resultUrl" />
            </div>
          </div>
          <button
            class="custom-btn2 btn-5"
            @click="copyUrl(resultUrl)"
          >
            copy
          </button>
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
      responseUrl: "",
      resultUrl: "",
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
      .get(`/survey/` + this.getSurveyId() + "/info")
      .then((res) => {
        console.log(res);
        this.responseUrl = this.$responseUrl + res.data.responseUrl;
        this.resultUrl = this.$resultUrl + res.data.resultUrl;
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

<style scoped>
.main-container {
  overflow: hidden !important;
  height: 100%;
}

#router {
  height: 100%;
}

.custom-btn2 {
  width: 60px;
  height: 30px;
  color: #fff;
  border-radius: 50px;
  padding: 5px 2px;
  margin-left: 2%;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 14px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;

  box-shadow: inset 1px 1px 1px 0px rgba(255, 255, 255, 0.5),
    inset -7px -7px 10px 0px rgba(0, 0, 0, 0.1),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  text-shadow: 2px 2px 3px rgba(255, 255, 255, 0.5),
    -4px -4px 6px rgba(116, 125, 136, 0.2);
  outline: none;
}

.btn-5 {
  border: none;
  color: white;
  background-color: #e39a52;
}
.btn-5:hover {
  color: black;
  background: transparent;
  box-shadow: none;
}
.btn-5:before,
.btn-5:after {
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #e39a52;
  box-shadow: -1px -1px 5px 0px #fff, 7px 7px 20px 0px #0003,
    4px 4px 5px 0px #0002;
  transition: 400ms ease all;
}
.btn-5:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}
.btn-5:hover:before,
.btn-5:hover:after {
  width: 100%;
  transition: 800ms ease all;
}
</style>
