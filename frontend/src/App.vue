<template>
  <div id="app">
    <Sidebar id="sidebar" v-if="showSide" />
    <div v-if="showFrame" id="main-frame">
      <!-- <button>로그아웃</button> -->
      <div class="logout">
        <a @click="onLogout">
          <i class="fas fa-sign-out-alt fa-2x"></i>
        </a>
      </div>
      <div id="background-frame">
        <RouterView id="router" :key="$route.fullPath" />
      </div>
    </div>
    <RouterView v-if="!showFrame" id="router" :key="$route.fullPath" />
  </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue";
import { mapActions } from 'vuex';

export default {
  name: "App",
  components: {
    Sidebar,
  },
  computed: {
    showSide() {
      return !(
        this.$route.name === "Form" ||
        this.$route.name === "Finish" ||
        this.$route.name == "ResultLink" ||
        this.$route.name == "Login" ||
        this.$route.name == "SurveyClosed"
      );
    },
    showFrame() {
      return !(
        this.$route.name === "Form" ||
        this.$route.name === "Finish" ||
        this.$route.name == "Login" ||
        this.$route.name == "SurveyClosed"
      );
    },
  },
  methods: {
    ...mapActions('user', ['onLogout']),
  }
};
</script>

<style>
@import "./assets/style/buttons.css";

#app {
  display: flex;
}

#router {
  width: 100%;
}
@import "./assets/style/main.css";
</style>
