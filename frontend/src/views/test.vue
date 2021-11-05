te
<template>
  <div id="wrapper">
    <button @click="endPage">to end</button>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "TestPage",
  data() {
    return {
      surveyId: "",
      templateId: "",
    };
  },
  methods: {
    ...mapGetters("template", [
      "getTemplateId",
      "getEndTime",
      "getTeamId",
      "getTitle",
      "getDescription",
    ]),
    ...mapGetters("question", ["getQuestionList", "getCategoryList"]),
    ...mapGetters("filterQuestion", ["getFilterQuestionList"]),
    ...mapActions("survey", ["setSurveyId"]),
    endPage() {
      this.saveTemplate();
    },
    saveTemplate() {
      axios
        .post("/template", {
          title: this.getTitle,
          desc: this.getDescription,
        })
        .then((res) => {
          this.templateId = res.data.templateId;

          this.saveQuestions();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    saveQuestions() {
      axios
        .post(`/template/${this.templateId}/questions`, {
          questionList: this.getQuestionList(),
          categoryList: this.getCategoryList(),
        })
        .then(() => {
          console.log("!@!@!@");
          this.saveSurvey();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    saveSurvey() {
      axios
        .post("/survey", {
          templateId: this.templateId,
          endTime: this.getEndTime(),
          teamId: this.getTeamId(),
        })
        .then((res) => {
          this.surveyId = res.data.surveyId;
          this.setSurveyId(this.surveyId);
          this.saveFilterQuestions();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    saveFilterQuestions() {
      console.log(this.getFilterQuestionList());
      axios
        .post(`/survey/${this.surveyId}/filters`, {
          filterQuestionList: this.getFilterQuestionList(),
        })
        .then(() => {
          this.$router.push({
            path: `/survey/complete`,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    editPage() {
      console.log("edit");
    },
  },
};
</script>
