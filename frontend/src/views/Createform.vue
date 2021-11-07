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
        <div class="page-title-div">
          <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">
            신규 설문 생성
          </h1>
          <button
            @click="endEditForm()"
            class="next-button yellow-button rounded-corner-button"
          >
            Next
          </button>
        </div>
        <hr style="width: 100%" />

        <div
          class="el-card box-card is-always-shadow"
          style="margin-top: 5vh; margin-left: 13vw; height: 50vh; width: 60vw"
        >
          <h3
            class="d-flex"
            style="font-size: 2.5rem; margin-left: 10vw; margin-top: 7vh"
          >
            설문 이름 :
            <input
              v-model="surveyTitle"
              type="text"
              style="
                height: 3vh;
                width: 20vw;
                font-size: 1.5rem;
                margin-left: 1vw;
              "
              placeholder="제목을 작성하세요."
            />
          </h3>
          <div class="d-flex" style="margin-left: 10vw; margin-top: 5vh">
            <h3 style="font-size: 2.5rem; margin-right: 1vw">설문 대상 :</h3>
            <div
              class="custom-select"
              style="margin-top: 0.5vh; font-size: 1.5rem"
            >
              <!-- <select name="team" v-model="targetTeamId">
                  <option value="">인사팀</option>
                  <option value="">홍보팀</option>
                  <option value="">보안팀</option>
                  <option value="">개발팀</option>
                </select> -->
              <select name="team" v-model="targetTeamId">
                <option
                  v-for="(team, teamIdx) in teamList"
                  :key="teamIdx"
                  :value="team.teamId"
                >
                  {{ team.teamName }}
                </option>
              </select>
            </div>
          </div>
          <!-- <div class="form-checkbox form-checkbox-inline" >
                <select name="team" v-model="targetTeamId">
                  <option v-for="(team, teamIdx) in teamList" :key="teamIdx" :value="team.teamId">
                    {{ team.teamName }}
                  </option>
                </select>
                <div class="custom-select"> -->
          <!-- </div> -->

          <div class="d-flex" style="margin-left: 10vw; margin-top: 5vh">
            <h3 style="font-size: 2.5rem; margin-right: 1vw">설문 기간 :</h3>
            <div style="margin-top: 0.2vh">
              <el-date-picker
                v-model="endDate"
                type="date"
                placeholder="종료 날짜를 선택하세요"
                default-value="2021-11-01"
              >
              </el-date-picker>
            </div>
          </div>

          <div class="d-flex" style="margin-left: 10vw; margin-top: 5vh">
            <h3 style="font-size: 2.5rem; margin-right: 1vw">설명 :</h3>
            <textarea
              @
              auto_textarea.html
              name="Text1"
              cols="40"
              rows="5"
              v-model="description"
              placeholder="설명을 입력해주세요."
              style="height: 10vh; width: 27vw; font-size: 1.5rem; resize: none"
            ></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script scoped>
import { mapState, mapActions } from "vuex";

export default {
  name: "Createform",
  data() {
    return {
      description: "",
      surveyTitle: "",
      targetTeamId: "",
      endDate: "",
    };
  },
  computed: {
    ...mapState("team", ["teamList"]),
    ...mapState("list", ["editSurveyId"]),
    ...mapState("survey", ["recentSurvey"]),
  },
  methods: {
    ...mapActions("template", [
      "setTemplateId",
      "setCreateTime",
      "setEndTime",
      "setTeamId",
      "setDescription",
      "setTitle",
    ]),
    ...mapActions("team", ["getTeams"]),
    ...mapActions("survey", ["getRecentSurveyInfo"]),
    endEditForm() {
      let endTime = this.endDate.toISOString().split("T");
      this.setEndTime(endTime[0] + " " + endTime[1].split(".")[0]);
      this.setTeamId(this.targetTeamId);
      this.setTitle(this.surveyTitle);
      this.setDescription(this.description);

      this.$router.push("/filter");

      // axios
      //   .post("/template", {
      //     title: this.surveyTitle,
      //     description: this.description,
      //   })
      //   .then((res) => {
      //     this.setTemplateId(res.data.templateId);

      //     this.$router.push("/filter");
      //   });
    },
    setRecentSurvey(){
      if(!this.editSurveyId){
        console.log("null!");
      }else{
        console.log("not null")
        this.getRecentSurveyInfo(this.editSurveyId)
        this.description = this.recentSurvey.description;
        this.surveyTitle = this.recentSurvey.title;
        this.targetTeamId = this.recentSurvey.teamId;
        console.log(this.surveyTitle)
      }
    }
  },
  created() {
    this.getTeams();
    this.setRecentSurvey();
  },
};
</script>

<style scoped>
.page-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-pills > li > a {
  border-radius: 0;
}
.rounded-corner-button {
  border-radius: 12px;
}
.yellow-button:hover {
  filter: brightness(115%);
}
.yellow-button {
  background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white;
}
.next-button {
  margin-top: 10%;
  padding-top: 1%;
  padding-bottom: 1%;
  text-align: center;
  /* height: 20%; */
  width: 10%;
}

#wrapper {
  padding-left: 0;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
  overflow: hidden;
}
#sidebar-wrapper {
  z-index: 1000;
  position: absolute;
  left: 100px;
  width: 0;
  height: 100%;
  margin-left: -100px;
  overflow-y: auto;
  background: white;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
}

.sidebar-nav {
  position: absolute;
  top: 0;
  width: 100px;
  margin: 0;
  padding: 0;
  list-style: none;
  margin-top: 2px;
}

.sidebar-nav li {
  text-indent: 15px;
  line-height: 40px;
}

.sidebar-nav li a {
  display: block;
  text-decoration: none;
  color: #999999;
}

.sidebar-nav li a:hover {
  color: rgb(5, 25, 58);
  background: black;
  width: 8%;
}

@media (min-width: 768px) {
  #wrapper {
    padding-left: 100px;
  }
  .fixed-brand {
    width: 100px;
  }
  #wrapper.toggled {
    padding-left: 0;
  }
  #sidebar-wrapper {
    width: 100px;
  }
}
.form-checkbox {
  position: relative;
  margin-top: 2.25rem;
  margin-bottom: 2.25rem;
  text-align: left;
}
.form-checkbox-inline .form-checkbox-label {
  display: inline-block;
  margin-right: 1rem;
}

.form-checkbox-legend {
  margin: 0 0 0.125rem 0;
  font-weight: 500;
  font-size: 1rem;
  color: #333;
}

.form-checkbox-label {
  position: relative;
  cursor: pointer;
  padding-left: 1.5rem;
  text-align: left;
  color: #333;
  display: block;
  margin-bottom: 0.5rem;
}

.form-checkbox-label:hover i {
  color: #337ab7;
}

.form-checkbox-label span {
  display: block;
}

.form-checkbox-label input {
  width: auto;
  opacity: 0.0001;
  position: absolute;
  left: 0.25rem;
  top: 0.25rem;
  margin: 0;
  padding: 0;
}

.form-checkbox-button {
  position: absolute;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  display: block;
  color: #999;
  left: 0;
  top: 0.25rem;
  width: 1rem;
  height: 1rem;
  z-index: 0;
  border: 0.125rem solid currentColor;
  border-radius: 0.0625rem;
  transition: color 0.28s ease;
  will-change: color;
}

.form-checkbox-button::before,
.form-checkbox-button::after {
  position: absolute;
  height: 0;
  width: 0.2rem;
  background-color: #337ab7;
  display: block;
  transform-origin: left top;
  border-radius: 0.25rem;
  content: "";
  transition: opacity 0.28s ease, height 0s linear 0.28s;
  opacity: 0;
  will-change: opacity, height;
}

.form-checkbox-button::before {
  top: 0.65rem;
  left: 0.38rem;
  transform: rotate(-135deg);
  box-shadow: 0 0 0 0.0625rem #fff;
}

.form-checkbox-button::after {
  top: 0.3rem;
  left: 0;
  transform: rotate(-45deg);
}

.form-checkbox-field:checked ~ .form-checkbox-button {
  color: #337ab7;
}

.form-checkbox-field:checked ~ .form-checkbox-button::after,
.form-checkbox-field:checked ~ .form-checkbox-button::before {
  opacity: 1;
  transition: height 0.28s ease;
}

.form-checkbox-field:checked ~ .form-checkbox-button::after {
  height: 0.5rem;
}

.form-checkbox-field:checked ~ .form-checkbox-button::before {
  height: 1.2rem;
  transition-delay: 0.28s;
}
.custom-select {
  width: 300px;
  height: 40px;
  background-color: #eee;
  position: relative;
}
.custom-select select {
  width: 100%;
  height: 100%;
  border: none;
  position: relative;
  z-index: 2;
  padding: 0 10px;
  background: transparent;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}
/* .custom-select:after{
    content: "S";
    display: block;
    width: 20px;
    height: 20px;
    line-height: 20px;
    text-align: center;
    background-color: #00f;
    color: #fff;
    position: absolute;
    top: 10px;
    right: 10px;
    z-index: 1;
} */
.custom-select > select:focus > option {
  padding: 10px 0 !important;
  -webkit-appearance: none;
}
</style>

