te
<template>
  <div id="wrapper">
    <button @click="editPage">to edit</button>
    <button @click="endPage">to end</button>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "TestPage",
  data() {
    return {};
  },
  methods: {
    ...mapGetters(["getQuestionList", "getTemplateId", "getEndTime", "getTeamId"]),
    ...mapActions(["setSurveyId"]),
    endPage() {
      console.log("test");

      let questionList = this.getQuestionList();

      console.log(questionList);

      // let templateId = this.getTemplateId();
      // let endTime = this.getEndTime();
      // let teamId = this.getTeamId();

      axios
        .post("/survey", {
          templateId: "Eg75YlYoi7lWO",
          endTime: "2021-10-10 14:22:33",
          teamId: "1234567891233",
        })
        .then((result) => {
          this.setSurveyId(result.data.surveyId);
          this.$router.push("/survey/complete");
        })
        .catch((err) => {
          console.log(err);
        });
      // this.$router.push({
      //   path: `/survey/complete`,
      // });
    },
    editPage() {
      console.log("edit");
    },
  },
};
</script>
