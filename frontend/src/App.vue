<template>
   <div>
      <Sidebar v-if="$route.name != 'Form' "/>
      <router-view></router-view>
   </div>
</template>

<script>
import Sidebar from "@/components/common/Sidebar.vue"


export default {
   name: "App",

   components: {
      Sidebar
   },
}  

</script>

<style>
 @import './assets/style/main.css';
</style>

