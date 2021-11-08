<template>
  <div id="wrapper">
    <div id="wrapper-div">
      <div id="container">
        <div id="intro">
          안녕하세요! 지금 <b>SSURBAR</b>를 통해 설문을 시작해보세요 📝
        </div>
        <div id="bottonBox">
          <div class="surveyButton" @click="clickScratch">
            <img class="buttonImg" src="@/assets/newdoc.png" />
            <span style="display: inline-block; vertical-align: middle">
              Start from scratch
              <h5>신규 설문 생성</h5>
            </span>
          </div>
          <div class="surveyButton" @click="clickTemplate">
            <img class="buttonImg" src="@/assets/edit.png" />
            <span style="display: inline-block; vertical-align: middle">
              Start from a template
              <h5>기본 서식에서 시작</h5>
            </span>
          </div>
          <div class="surveyButton" @click="clickPastSurvey">
            <img class="buttonImg" src="@/assets/copy.png" />
            <span style="display: inline-block; vertical-align: middle">
              Copy a past survey
              <h5>지난 설문조사 복사</h5>
            </span>
          </div>
        </div>

        <hr />

        <div id="tab">
          <span>진행중인 설문</span> | <span>완료된 설문</span>
        </div>
        <ongoingList></ongoingList>
      </div>
    </div>
  </div>
</template>

<script>
import ongoingList from "@/views/main/ongoingList.vue";
import { mapActions, mapState } from "vuex";
export default {
  name: "App",
  components: {
    ongoingList,
  },
  computed: {
    ...mapState("survey", ["surveyCreateType"]),
  },
  methods: {
    ...mapActions("survey", ["setCreateType"]),
    clickScratch() {
      this.setCreateType(this.surveyCreateType.NEW);
      this.$router.push("/form/createform");
    },
    clickTemplate() {
      this.setCreateType(this.surveyCreateType.DEFAULT);
      this.$router.push("/template/default");
    },
    clickPastSurvey() {
      this.setCreateType(this.surveyCreateType.RECENT);
      this.$router.push("/template/recent");
    },
  },
};
</script>

<style scoped>
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
#wrapper-div {
  background-position: center;
  background-color: white;
  margin-top: 3%;
  margin-left: 4%;
  margin-right: 4%;
  height: 90vh;
  border-radius: 60px 60px 0% 0%;
}
#intro {
  font-size: 2.5em;
  text-align: center;
  margin-top: 5%;
  margin-bottom: 3%;
}
#container {
  padding: 4%;
  padding-right: 4%;
}
#bottonBox {
  text-align: center;
  margin-bottom: 5%;
}
#bottonBox .surveyButton:hover {
  transform: scale(1.1);
}
#tab {
  margin-top: 2.5%;
}
.surveyButton {
  width: 25%;
  border-radius: 10px 10px 10px 10px;
  padding: 2% 5% 2% 5%;
  display: inline-block;
  margin-right: 5%;
  box-shadow: 2px 2px 2px 2px rgb(206, 206, 206);
  font-size: 0.5em;
  color: rgb(167, 167, 167);
}
.surveyButton > span > h5 {
  font-weight: 600;
  color: black;
}
.buttonImg {
  height: 50px;
  display: inline-block;
  margin-right: 5%;
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
</style>
