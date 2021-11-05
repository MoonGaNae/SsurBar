te
<template>
   <div id="wrapper">
    <div id="sidebar-wrapper">
      <ul class="sidebar-nav nav-pills nav-stacked" id="menu">
        <li style="margin-top: 25vh; margin-left: 5%">
          <a href="#"><i class="fas fa-home fa-2x" style="margin-top: 1vh"></i></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"><i class="fas fa-plus-square fa-2x" style="margin-top: 1vh"></i></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"><i class="fas fa-chart-bar fa-2x" style="margin-top: 1vh"></i></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"><i class="fas fa-cog fa-2x" style="margin-top: 1vh"></i></a>
        </li>
      </ul>
    </div>
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
        <div class="container" style="height:100%">
          <div class="row justify-content-md-center">
            <div class="col">
              <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">기본 서식 조회</h1>
            </div>
            <div class="col-md-auto align-self-end">
              <el-input placeholder="Please input" v-model="searchTemplate" class="input-with-select">
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            </div>
            
          </div>
          <hr class="mb-5" style="width: 100%" />
          <div class="row row-cols-2" style="height:50%;"> 
            <default-template-item v-on:click.native="moveTemplatePreview(template.templateId)" v-for="(template, index) in defaultTemplateList" :key="index" :template="template"> </default-template-item>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';
import DefaultTemplateItem from '../components/DefaultTemplateItem.vue';

export default {
  name: "DefaultTemplateList",
  components: {
    DefaultTemplateItem
  },
  data() {
    return {
      searchTemplate: '',
    };
  },
  computed: {
    ...mapState("template",['defaultTemplateList']),
  },
  methods: {
    ...mapActions("template",['getDefaultTemplates']),
    moveTemplatePreview(templateId){
      this.$router.push("/template/"+templateId+"/preview");
    },
  },
  created () {
    this.getDefaultTemplates();
  }
};
</script>

