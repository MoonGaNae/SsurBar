<template>
  <div id="wrapper">
    <div class="main-container">
      <div class="page-title-div row justify-content-md-center">
        <div class="page-title-div-child">
          <h1>설문서식 미리보기</h1>
        </div>
      </div>

      <div class="surveyForm">
        <div class="survey-des-div">
          <div class="survey-des">
            <h1 class="title">{{ getTitle() }}</h1>
            <p class="description">
              {{ getDescription() }}
            </p>
          </div>
          <div class="button-div col-md-auto align-self-end">
            <button class="custom-btn1 btn-5" @click="moveCreateForm()">
              <span>생성</span>
            </button>
            <button class="custom-btn1 btn-5" @click="moveBack()">
              <span>나가기</span>
            </button>
          </div>
        </div>

        <div class="surveyContent">
          <el-form ref="form">
            <el-collapse>
              <el-collapse-item title="Filters" name="1">
                <div
                  class="surveytitle"
                  v-for="(question, questionIdx) in filters"
                  :key="questionIdx"
                >
                  Q. {{ question.title }}
                  <br />
                  <el-form-item>
                    <el-radio-group v-model="form.questionRes[questionIdx]">
                      <div
                        class="surveytitle"
                        v-for="(example, exampleIdx) in question.content"
                        :key="exampleIdx"
                      >
                        <el-radio
                          :label="example"
                          style="display: block; margin-top: 1.5em"
                        ></el-radio>
                      </div>
                    </el-radio-group>
                  </el-form-item>
                </div>
              </el-collapse-item>

              <el-collapse-item
                v-for="(item, idx) in questions"
                :key="idx"
                :title="idx"
              >
                <div
                  class="surveytitle"
                  v-for="(question, questionIdx) in item"
                  :key="questionIdx"
                >
                  Q. {{ question.title }}
                  <br />
                  <el-form-item>
                    <el-radio-group
                      v-model="form.questionRes[idx + questionIdx]"
                    >
                      <div
                        class="surveytitle"
                        v-for="(example, exampleIdx) in question.content"
                        :key="exampleIdx"
                      >
                        <el-radio
                          :label="example"
                          style="display: block; margin-top: 1.5em"
                        ></el-radio>
                      </div>
                    </el-radio-group>
                  </el-form-item>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-form>
        </div>
        <div style="text-align: center">
          <img class="logo" src="@/assets/smalllogo.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState, mapActions, mapGetters } from "vuex";

export default {
  name: "CreatePreview",
  data() {
    return {
      surveyId: "",
      templateId: "",

      filters: [],
      questions: [],

      form: {
        questionRes: [],
      },
    };
  },
  computed: {
    ...mapState("survey", ["surveyCreateType", "curCreateType"]),
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
    moveCreateForm() {
      if (this.curCreateType == this.surveyCreateType.NEW) {
        this.saveTemplate();
      } else {
        this.templateId = this.getTemplateId();
        this.saveQuestions();
      }
    },
    moveBack() {
      this.$router.back();
    },
    saveTemplate() {
      axios
        .post("/template", {
          title: this.getTitle(),
          description: this.getDescription(),
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
      // console.log(this.getFilterQuestionList());
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

    // 객체배열에서, key값을 가지고, 그룹핑하기
    groupBy(data, key) {
      return data.reduce((carry, el) => {
        var group = el[key];

        if (carry[group] === undefined) {
          carry[group] = [];
        }

        carry[group].push(el);
        return carry;
      }, {});
    },
    getFilters() {
      const filterList = new Array();

      this.getFilterQuestionList().forEach((el) => {
        // 필터질문 역직렬화
        var filter = JSON.parse(el);
        filter.content = JSON.parse(filter.content);

        filterList.push(filter);
      });

      this.filters = filterList;
    },
    getQuestions() {
      const questionList = new Array();

      this.getQuestionList().forEach((el) => {
        var question = JSON.parse(el);

        // 질문 역직렬화
        question.content = JSON.parse(question.content);
        questionList.push(question);
      });

      // 카테고리별 그룹핑
      this.questions = this.groupBy(questionList, "categoryName");
    },
  },
  created() {
    this.getQuestions();
    this.getFilters();
  },
};
</script>

<style scoped>
/* @import "../assets/style/buttons.css"; */

button {
  margin: 20px;
}

.page-title-div-child > h1 {
  font-size: 4rem;
}

.button-div {
  display: flex;
  min-width: 10vw;
  justify-content: flex-end;
}

.survey-des-div {
  display: flex;
  justify-content: space-between;
}

.page-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 15vh;
}
</style>
