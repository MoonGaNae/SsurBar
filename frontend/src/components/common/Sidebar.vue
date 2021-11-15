<template>
  <div id="sidebar">
    <div id="sidebar-wrapper">
      <ul class="sidebar-nav nav-pills nav-stacked" id="menu">
        <li style="margin-left: 5%">
          <router-link to="#">
            <a @click="movePage(`main`)"
              ><i class="fas fa-home fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <li style="margin-left: 10%">
          <router-link to="#">
            <a @click="movePage(`FromCreateion`)"
              ><i class="fas fa-plus-square fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <!-- <li style="margin-top: 10vh; margin-left: 10%">
          <router-link to="/">
            <a href="#"
              ><i class="fas fa-chart-bar fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li> -->

        <li style="margin-left: 10%" v-if="showCertificate">
          <router-link to="#">
            <a @click="movePage(`Certification`)"
              ><i class="fas fa-cog fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>
      </ul>
    </div>
    <!-- <div style="background-color: rgb(5, 25, 58); height: 100vh">
      <div>네브바같은 느낌으로다가</div>
      <div
        style="
          background-position: center;
          background-color: white;
          margin-top: 5%;
          margin-left: 7%;
          margin-right: 7%;
          height: 90vh;
          border-radius: 60px 60px 0% 0%;
        "
      ></div>
    </div> -->
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "Sidebar",
  computed: {
    ...mapState("user", ["userInfo"]),
    showCertificate() {
      return this.userInfo.userType=="ADMIN";
    }
  },
  methods: {
    ...mapActions("question", ["initQuestionData"]),
    ...mapActions("analysis", ["initAnalysisData"]),
    ...mapActions("filterQuestion", ["initFilterQuestionData"]),
    ...mapActions("template", ["initTemplateData"]),
    ...mapActions("list", ["initListData"]),
    movePage(pageName) {
      this.initQuestionData();
      this.initAnalysisData();
      this.initFilterQuestionData();
      this.initTemplateData();
      this.initListData();

      this.$router.push({ name: pageName });
    },
  },
};
</script>

<style>
.nav-pills > li > a {
  border-radius: 0;
}

#sidebar {
  padding-left: 0;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
  overflow: hidden;
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
  #sidebar {
    padding-left: 100px;
  }
  .fixed-brand {
    width: 100px;
  }
  #sidebar.toggled {
    padding-left: 0;
  }
  #sidebar-wrapper {
    width: 100px;
  }
}
</style>

<style scoped>
#menu {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100vh;
}

#menu li {
  margin-top: 5vh;
  margin-bottom: 5vh;
}
</style>
