<template>
  <div id="app">
    <Sidebar id="sidebar"  v-if="showSide"/>
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
   computed:{
         showSide(){
            return !(
               this.$route.name === "Form" ||
               this.$route.name  === "Finish"
            );
         }
   }
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


