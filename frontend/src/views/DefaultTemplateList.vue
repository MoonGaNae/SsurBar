te
<template>
  <div id="wrapper">
    <div class="container">
      <div class="row justify-content-md-center">
        <div class="col">
          <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">
            기본 서식 조회
          </h1>
        </div>
        <div class="col-md-auto align-self-end">
          <el-input
            placeholder="Please input"
            v-model="searchTemplate"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
      </div>
      <hr class="mb-5" style="width: 100%" />
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
