<template>
  <div id="app">
    <Sidebar v-if="$route.name != 'Form'" id="sidebar" />
    <RouterView id="router" :key="$route.fullPath" />
  </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue";

export default {
  name: "App",
  components: {
    Sidebar,
  },
};
</script>

<style>
#app {
  display: flex;
}

#router {
  width: 100%;
}
@import "./assets/style/main.css";
</style>
