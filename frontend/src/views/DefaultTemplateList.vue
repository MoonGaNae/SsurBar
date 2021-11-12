te
<template>
  <div>
    <div class="page-title-div ">
      <h1 style="margin-left: 15%; margin-top: 15%; font-size: 3rem;width:100%">         
        기본 서식 조회
      </h1>
    </div>
    <hr style="width: 70%; margin-left:15%; margin-top:6%" />
    <div id="wrapper">
      <div class="container">
        <!-- <div class="row justify-content-md-center">
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
        </div> -->
        
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
</style>