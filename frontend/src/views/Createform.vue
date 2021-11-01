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
        <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">신규 설문 생성</h1>
        <hr style="width: 90%" />
        <div>
          <h3 style="d-flex; text-align:center; font-size:2.5rem">
            설문 이름 :
            <input
              v-model="surveyTitle"
              type="text"
              style="height: 3vh; width: 27vw; font-size: 2.5rem"
            />
          </h3>
        </div>
        <div>
          <h3 style="d-flex; text-align:center; font-size:2.5rem">
            설문 대상
            <div class="form-checkbox form-checkbox-inline" style="d-flex; text-align:center;">
              <label class="form-checkbox-label" v-for="(team, teamIdx) in teamList" :key="teamIdx">
                <input
                  v-model="targetTeams"
                  name="rap"
                  class="form-checkbox-field"
                  type="checkbox"
                  :value="team.teamId"
                />
                <i class="form-checkbox-button"></i>
                <span style="font-size: 2rem">{{ team.teamName }}</span>
              </label>
              <!-- <label class="form-checkbox-label">
            <input v-model="targetTeams" name="rap" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">인사팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="pop" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">개발팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="rock" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">기획팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="metal" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">보안팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="r_b" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">재경팀</span>
          </label> -->
            </div>
            <!-- <div class="form-checkbox form-checkbox-inline" style="d-flex; text-align:center;">
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="rap" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">총무팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="pop" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">마케팅팀</span>
          </label>
          <label class="form-checkbox-label">
            <input v-model="targetTeams" name="rock" class="form-checkbox-field" type="checkbox" />
            <i class="form-checkbox-button"></i>
            <span style="font-size: 2rem">타회사</span>
          </label>
        </div> -->
          </h3>
          <div style="d-flex; text-align:center">
            <h3 style="font-size: 2.5rem">설문 기간</h3>
            <div class="block" style="d-flex; ">
              <el-date-picker
                v-model="endDate"
                type="date"
                placeholder="Pick a date"
                default-value="2010-10-01"
              >
              </el-date-picker>
            </div>
          </div>
          <div style="d-flex; text-align:center;">
            <h3 style="font-size: 2.5rem">설명</h3>

            <textarea
              v-model="description"
              placeholder="여러줄을 입력해보세요"
              style="height: 10vh; width: 27vw; font-size: 2rem"
            ></textarea>

            <el-button>생성</el-button>
          </div>
        </div>
        <button @click="nextPage()">다음</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";

export default {
  name: "Createform",
  data() {
    return {
      description: "",
      surveyTitle: "",
      targetTeams: [],
      endDate: "",
      teamList: [],
    };
  },
  methods: {
    nextPage() {
      console.log(this.description);
      console.log(this.surveyTitle);
      console.log(this.targetTeams);
      console.log(this.endDate);
    },
    getTeamList() {
      axios
        .get("/team")
        .then((res) => {
          res.data.teamList.forEach((el) => {
            let team = {
              teamId: el.teamId,
              teamName: el.teamName,
            };

            this.teamList.push(team);
          });

          console.log(this.teamList);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  mounted() {
    this.getTeamList();
  },
};
</script>

<style>
.nav-pills > li > a {
  border-radius: 0;
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
</style>
