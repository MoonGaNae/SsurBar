<template>
  <div> 
    <div class="page-title-div" style="padding-top:9%; padding-left:5%">
      <div class="page-title-div-child" >
        <h1>신규 설문 생성</h1>
      </div>
    </div>
    <hr style="width: 90%; margin-top:3%; margin-left:5%;" />
    
    <button 
      @click="endEditForm()" 
      class="custom-btn btn-5" 
      style="margin-left:86%" >
      <span>Next</span></button>
    
    <div class="container">
      <div
        class="row el-card box-card is-always-shadow"
        style="
          width: 60%;
          margin-left: 20%;
          border-radius: 25px;
          margin-bottom:5%;
        "
      >
        <div
          class="col-xs-12 col-lg-offset-3 col-lg-6"
          style="margin-left: 25%"
        >
          <form method="GET" action="" id="survey-form" name="survey-form">
            <fieldset>
              <label for="name" id="name-label" style="margin-top: 10%">
                설문 이름 *
              </label>
              <input
                class="name"
                type="text"
                id="name"
                name="name"
                style="font-size: 1.1em"
                v-model="surveyTitle"
                placeholder="Enter your name (required)"
                required
              />
            </fieldset>

            <fieldset>
              <label for="dropdown" style="margin-top: 10%">
                설문대상 *
              </label>
              <select
                name="team"
                v-model="targetTeamId"
                style="font-size: 1.1em"
                class="m-t-xs"
              >
                <option
                  v-for="(team, teamIdx) in teamList"
                  :key="teamIdx"
                  :value="team.teamId"
                >
                  {{ team.teamName }}
                </option>
              </select>
            </fieldset>

            <fieldset>
              <label for="name" id="name-label" style="margin-top: 10%">
                설문 기한 *
                <div style="margin-top: 5%">
                  <el-date-picker
                    v-model="endDate"
                    type="date"
                    placeholder="종료 날짜를 선택하세요"
                    default-value="2021-11-01"
                  >
                  </el-date-picker>
                </div>
              </label>
            </fieldset>
            <fieldset>
              <label for="survey-form-suggestions"> 설명 </label>
              <textarea
                id="survey-form-suggestions"
                maxlength="194"
                style="font-size: 1.2em; margin-bottom: 10%; margin-top: -5%"
                v-model="description"
              ></textarea>
            </fieldset>
          </form>
        </div>
      </div>
    </div>
  </div>
  
</template>

<style lang="scss" scoped>
// $font: "Roboto", sans-serif;
$bg: white;
$grey: #333;
$rose: #e87e91;
$blue-light: #73bec8;
$blue-lighter: #abdad3;
$green: #333;
$red: #ef5350;

// Margin & Padding
.m-b-xs {
  margin-bottom: 2%;
}
.m-b-md {
  margin-bottom: 10%;
}
.m-t-xs {
  margin-top: 2%;
}
.m-t-sm {
  margin-top: 5%;
}

// General
* {
  outline: 0 !important;
}

body {
  padding: 2% 0;
  background-color: $bg;
}

a {
  color: $grey;

  &:hover {
    text-decoration: none;
    color: $grey;
  }
}

#survey-form {
  label,
  .labels {
    display: block;
    margin-bottom: 8%;
    // font-family: $font;
    font-size: 1.4em;
    // font-weight: bolder;
    color: $grey;
    letter-spacing: 0.5px;
  }

  input::-webkit-input-placeholder {
    color: transparent !important;
  }
  input::-moz-placeholder {
    color: transparent !important;
  }
  input:-ms-input-placeholder {
    color: transparent !important;
  }
  input:-moz-placeholder {
    color: transparent !important;
  }

  input,
  select {
    display: block;
    width: 100%;
    overflow: hidden;
    outline: none;
    border: 2px solid $grey;
  }

  input {
    margin-top: 1.5%;
    padding: 0 0 5px 0;
    background: transparent;
    border: none;
    outline: none;
    border-bottom: 2px solid $grey;
    font-size: 1.1em;
    font-weight: 300;
    color: $green;

    &:focus {
      border-color: $green;
    }
  }

  [type="checkbox"] {
    display: inline-block;
    width: auto;
    margin: 0px 10px 0 0;
  }

  [type="checkbox"] {
    display: inline-block;
    margin: 0 10px 0 0 !important;
    position: relative;
    top: 5px;
    right: 0;
    bottom: 0;
    left: 0;
    -webkit-appearance: none;
    -moz-appearance: none;
    -ms-appearance: none;
    -o-appearance: none;
    appearance: none;
    height: 23px;
    width: 23px;
    transition: all 0.15s ease-out 0s;
    background: $bg;
    color: $grey;
    cursor: pointer;
    outline: none;
    z-index: 1000;
    border: 1px solid #fff;

    &:hover {
      border-color: $green;
    }
    &:checked {
      &:before {
        display: inline-block;
        height: 21px;
        width: 21px;
        position: relative;
        left: 0;
        bottom: 0;
        content: "\e014";
        text-align: center;
        font-family: "Glyphicons Halflings";
        line-height: 20px;
        font-size: 15px;
        color: $green;
      }
    }
    &:focus {
      outline: none;
      border-color: $grey;
    }
  }

  [type="radio"] {
    border-radius: 50%;

    &:after {
      border-radius: 50%;
    }
  }

  [type="number"] {
    width: 45px;

    &::-webkit-inner-spin-button,
    &::-webkit-outer-spin-button {
      -webkit-appearance: none;
      -moz-appearance: none;
      appearance: none;
      margin: 0;
    }
  }

  select {
    height: 40px;
    padding-left: 5px;
    background-color: $bg;
    border-bottom: 2px solid $grey;
    color: $green;

    & option {
      padding: 5px 10px;
      font-weight: 300;
      cursor: pointer;

      &:hover {
        background-color: $green;
      }
    }
  }

  textarea {
    resize: none;
    margin-top: 2%;
    padding: 10px 10px 0px 20px;
    width: 100%;
    height: 90px;
    color: $green;
    background-color: $bg;
    border: 2px solid $grey;
  }

  .btn {
    display: inline-block;
    position: relative;
    width: 100%;
    margin: 3% 0 0 0;
    height: 45px;
    text-transform: uppercase;
    text-decoration: none;
    cursor: pointer;
    border: 3px solid #85ffc7;
    border-radius: 0;
    font-weight: 500;
    font-size: 1.2em;
    color: $green;
    text-align: center;
    background: none;
    transition: color 0.25s ease;

    &:after {
      position: absolute;
      content: "";
      top: 0;
      left: 0;
      width: 0;
      height: 100%;
      background-color: $green;
      transform-origin: left;
      transition: width 0.5s ease;
      z-index: -1;
    }
    &:hover {
      color: $bg;

      &:after {
        width: 100%;
      }
    }
  }

  fieldset {
    margin: 5% 0 0 0;
  }
}

.copyright {
  text-align: center;
  font-size: 10px;
  color: $grey;

  & i {
    color: $red;
  }
}

// Responsive
@media only screen and (max-width: 600px) {
  body {
    padding: 8% 0;
  }
}
</style>
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
      console.log(this.surveyTitle);
      if (!this.checkForm()) {
        this.$fire({
          title: "응답실패",
          text: "아직 설정하지 않은 항목이 존재합니다!",
          type: "error",
        });
      } else {
        let endTime = this.endDate.toISOString().split("T");
        this.setEndTime(endTime[0] + " " + endTime[1].split(".")[0]);
        this.setTeamId(this.targetTeamId);
        this.setTitle(this.surveyTitle);
        this.setDescription(this.description);

        this.$router.push("/filter");
      }
    },
    checkForm() {
      if (
        !this.description ||
        !this.surveyTitle ||
        !this.targetTeamId ||
        !this.endDate
      ) {
        return false;
      }
      return true;
    },
    async setRecentSurvey() {
      if (!this.editSurveyId) {
        console.log("null!");
      } else {
        console.log("not null");
        this.$fire({
          title: "지난 설문 불러오기",
          text: "선택한 설문지의 서식을 불러옵니다.",
          type: "success",
        });
        await this.getRecentSurveyInfo(this.editSurveyId);
        this.description = this.recentSurvey.description;
        this.surveyTitle = this.recentSurvey.title;
        this.targetTeamId = this.recentSurvey.teamId;
      }
    },
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
  height: 15vh;
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

.page-title-div-child > h1 {
  font-size: 4rem;
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

button {
  margin: 20px;
}
</style>
