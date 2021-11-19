<template>
  <div id="sidebar">
    <div id="sidebar-wrapper">
      <img class="logo" src="@/assets/biglogo1.png" />
      <ul class="sidebar-nav nav-pills nav-stacked" id="menu">
        <li style="margin-left: 2px">
          <router-link to="#">
            <a @click="movePage(`main`)"
              ><i class="fas fa-home fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>

        <li style="margin-left: 2px">
          <router-link to="#">
            <a @click="movePage(`FromCreateion`)"
              ><i
                class="fas fa-plus-square fa-2x"
                style="margin-top: 1vh; margin-left: 5px"
              ></i
            ></a>
          </router-link>
        </li>

        <li style="margin-left: 2px" v-if="showCertificate">
          <router-link to="#">
            <a @click="movePage(`Certification`)"
              ><i class="fas fa-cog fa-2x" style="margin-top: 1vh"></i
            ></a>
          </router-link>
        </li>
      </ul>
      <div class="logout">
        <a @click="onLogout">
          <i class="fas fa-sign-out-alt fa-2x"></i>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "Sidebar",
  computed: {
    ...mapState("user", ["userInfo"]),
    showCertificate() {
      return this.userInfo.userType == "ADMIN";
    },
  },
  methods: {
    ...mapActions("user", ["onLogout"]),
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
.logo {
  margin-top: 15%;
  margin-left: 5%;
  width: 85px;
}

#sidebar-wrapper {
  width: 100px;
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
  width: 100%;
  height: 100vh;
}

#menu li {
  margin-top: 5vh;
  margin-bottom: 5vh;
}

.logout {
  width: 100%;
  position: absolute;
  bottom: 15px;
  display: flex;
  justify-content: center;
  padding-left: 10px;
  filter: invert(53%) sepia(96%) saturate(11%) hue-rotate(7deg) brightness(95%)
    contrast(91%);
}

.logout:hover {
  filter: invert(100%) sepia(100%) saturate(0%) hue-rotate(138deg)
    brightness(104%) contrast(101%);
}
</style>
