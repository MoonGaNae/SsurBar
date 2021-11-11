<template>
  <div id="wrapper">
    <div class="container">
      <div class="page-title-div row justify-content-md-center">
        <!-- <h1 style="margin-left: 5%; margin-top: 6%; font-size: 3rem;">
          설문서식 미리보기
        </h1> -->
        <div class="col">
          <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">
            설문서식 미리보기
          </h1>
        </div>
        <div class="col-md-auto align-self-end">
          <!-- <el-input placeholder="Please input" v-model="searchTemplate" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input> -->
          <!-- <button
            class="next-button yellow-button rounded-button"
            @click="moveCreateForm()"
          >
            Create
          </button> -->
          <button class="custom-btn1 btn-5" @click="moveCreateForm()">
            <span>생성</span>
          </button>
          <button class="custom-btn1 btn-5" @click="moveBack()">
            <span>나가기</span>
          </button>

          <!-- <button
            class="next-button red-button rounded-button ms-3"
            @click="moveBack()"
          >
            Exit
          </button> -->
        </div>
      </div>
      <hr style="width: 88%; margin-left:6%; ma" />

      <div class="surveyForm">
        <el-tag type="danger" effect="plain" style="border-radius: 50px"
          >1일남음</el-tag
        >
        <div class="surveyDes">
          <!-- <h1 class="title">{{title}}</h1> -->
          <h5 class="date">2021.10.25~2021.10.28</h5>
          <!-- <p class="description">{{description}}</p> -->
        </div>
        <hr />
        <div class="surveyContent">
          <!-- <el-form ref="form" v-model="form">
                <el-collapse>
                    <el-collapse-item v-for="(item, idx) in category" :key="idx" :title="item.categoryName">
                        <div class="surveytitle" v-for="(question, questionIdx) in questions" :key="questionIdx">
                            <div v-if="item.categoryId==question.categoryId">
                                {{question.questionNum}}. {{question.title}} 
                                <br>
                                <el-form-item>
                                    <el-radio-group v-model="form.questionRes[questionIdx]">
                                        <div class="surveytitle" v-for="(example, exampleIdx) in questionExample[questionIdx].content" :key="exampleIdx">
                                            <el-radio :label="example" style="display: block; margin-top:1.5em;"></el-radio>
                                        </div> 
                                    </el-radio-group>
                                </el-form-item>
                                </div> 
                            </div>
                    </el-collapse-item>
                </el-collapse>
            </el-form> -->

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
    <!-- <button @click="saveTemplate">to end</button> -->
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState, mapActions, mapGetters } from "vuex";

export default {
  name: "TestPage",
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
button {
  margin: 20px;
}

.custom-btn1 {
  width: 95px;
  height: 30px;
  color: #fff;
  border-radius: 50px;
  padding: 2px 5px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 13px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    inset -7px -7px 10px 0px rgba(0, 0, 0, 0.1),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  text-shadow: 2px 2px 3px rgba(255, 255, 255, 0.5),
    -4px -4px 6px rgba(116, 125, 136, 0.2);
  outline: none;
}

.btn-5 {
  border: none;
  color: white;
  background-color: #e39a52;
}
.btn-5:hover {
  color: black;
  background: transparent;
  box-shadow: none;
}
.btn-5:before,
.btn-5:after {
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #e39a52;
  box-shadow: -1px -1px 5px 0px #fff, 7px 7px 20px 0px #0003,
    4px 4px 5px 0px #0002;
  transition: 400ms ease all;
}
.btn-5:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}
.btn-5:hover:before,
.btn-5:hover:after {
  width: 100%;
  transition: 800ms ease all;
}

.page-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 15vh;
}
</style>
