<template>
  <div>
    <div class="page-title-div" style="padding-top:9%; padding-left:5%">
      <div class="page-title-div-child" >
        <h1>기본 서식 조회</h1>
      </div>
    </div>
    <hr style="width: 90%; margin-top:3%; margin-left:5%;" />
    <el-input
      placeholder="Please input"
      v-model="searchTemplate"
      class="input-with-select"
      style="margin-left:80%; margin-bottom:4%;width:15%"
    >
      <el-button slot="append" icon="el-icon-search"></el-button>
    </el-input>
    <div class="container">
      <div class="row row-cols-2" style="height: 50%">
        <default-template-item
          v-on:click.native="moveTemplatePreview(template.templateId)"
          v-for="(template, index) in defaultTemplateList"
          :key="index"
          :template="template"
        >
        </default-template-item>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import DefaultTemplateItem from "../components/DefaultTemplateItem.vue";

export default {
  name: "DefaultTemplateList",
  components: {
    DefaultTemplateItem,
  },
  data() {
    return {
      searchTemplate: "",
    };
  },
  computed: {
    ...mapState("template", ["defaultTemplateList"]),
  },
  methods: {
    ...mapActions("template", ["getDefaultTemplates"]),
    moveTemplatePreview(templateId) {
      this.$router.push("/template/" + templateId + "/preview");
    },
  },
  created() {
    this.getDefaultTemplates();
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

.page-title-div-child > h1 {
  font-size: 4rem;
}
</style>