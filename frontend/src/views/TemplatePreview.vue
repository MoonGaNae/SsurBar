<template>
  <div id="wrapper">
    <div class="container">
      <div class="page-title-div row justify-content-md-center">
        <div class="col">
          <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">
            설문서식 미리보기
          </h1>
        </div>
        <div class="col-md-auto align-self-end">
          <!-- <el-input placeholder="Please input" v-model="searchTemplate" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input> -->
          <button
            class="next-button yellow-button rounded-button"
            @click="moveCreateForm()"
          >
            Create
          </button>
          <button
            class="next-button red-button rounded-button ms-3"
            @click="moveTemplateDefault()"
          >
            Exit
          </button>
        </div>
      </div>
      <hr style="width: 100%" />
      <div class="surveyForm">
        <el-tag type="danger" effect="plain" style="border-radius: 50px"
          >1일남음</el-tag
        >
        <div class="surveyDes">
          <h1 class="title">{{ title }}</h1>
          <h5 class="date">2021.10.25~2021.10.28</h5>
          <p class="description">{{ description }}</p>
        </div>
        <hr />
        <div class="surveyContent">
          <el-form ref="form" v-model="form">
            <el-collapse>
              <el-collapse-item
                v-for="(item, idx) in category"
                :key="idx"
                :title="item.categoryName"
              >
                <div
                  class="surveytitle"
                  v-for="(question, questionIdx) in questions"
                  :key="questionIdx"
                >
                  <div v-if="item.categoryId == question.categoryId">
                    {{ question.questionNum }}. {{ question.title }}
                    <br />
                    <el-form-item>
                      <el-radio-group v-model="form.questionRes[questionIdx]">
                        <div
                          class="surveytitle"
                          v-for="(example, exampleIdx) in questionExample[
                            questionIdx
                          ].content"
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

<script scoped>
import axios from "@/utils/axios.js";
import { mapActions } from "vuex";
export default {
  name: "TemplatePreview",
  data() {
    return {
      title: "",
      description: "",
      form: {
        filterTitle: [],
        filterRes: [],
        questionId: [],
        questionRes: [],
      },
      tempaletId: "",
      category: [
        {
          categoryId: "",
          categoryName: "",
        },
      ],
      questions: [],
    };
  },
  methods: {
    ...mapActions("template", ["setTemplateId"]),
    ...mapActions("question", ["setQuestionList", "setCategoryList"]),
    getTemplateInfo(templateId) {
      axios
        .get("/template/" + templateId + "/info")
        .then((res) => {
          this.title = res.data.title;
          this.description = res.data.description;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getQuestionList(templateId) {
      axios.get("template/" + templateId + "/questions").then((res) => {
        console.log(res);
        this.questions = res.data.questionList;

        var contents = new Array();
        var categorys = new Array();
        var questionExs = new Array();

        for (var i = 0; i < this.questions.length; i++) {
          contents[i] = this.questions[i].content;
          categorys.push({
            categoryId: this.questions[i].categoryId,
            categoryName: this.questions[i].categoryName,
          });
          questionExs.push({
            questionId: this.questions[i].questionId,
            content: JSON.parse(contents[i]),
          });
          this.form.questionId.push(this.questions[i].questionId);
          console.log(categorys);
        }
        var uniqueCategory = this.removeDuplicates(categorys, "categoryId");
        console.log("uniqueArray is: " + JSON.stringify(uniqueCategory));

        this.category = uniqueCategory;
        this.questionExample = questionExs;
        console.log(this.form);
      });
    },
    // 카테고리 중복값을 제거하기 위한 메소드
    removeDuplicates(originalArray, prop) {
      var newArray = [];
      var lookupObject = {};

      for (var i in originalArray) {
        lookupObject[originalArray[i][prop]] = originalArray[i];
      }

      for (i in lookupObject) {
        newArray.push(lookupObject[i]);
      }
      return newArray;
    },
    process() {
      // this.templateId = "1234657891234";
      this.templateId = this.$route.params.templateId;
      this.getTemplateInfo(this.templateId);
      this.getQuestionList(this.templateId);
    },
    moveTemplateDefault() {
      this.$router.push("/template/default");
    },
    moveCreateForm() {
      this.setTemplateId(this.templateId);
      // JSON 직렬화 시켜서 store에 저장
      let jsonList = new Array();
      this.questions.forEach((el) => {
        jsonList.push(JSON.stringify(el));
      });

      this.setQuestionList(jsonList);
      this.$router.push("/form/createform");
    },
  },
  created() {
    this.process();
  },
};
</script>

<style scoped>
.nav-pills > li > a {
  border-radius: 0;
}

#wrapper {
  padding-left: 0;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
  overflow: hidden;
}
#sidebar-wrapper {
  z-index: 1000;
  position: absolute;
  left: 100px;
  width: 0;
  height: 100%;
  margin-left: -100px;
  overflow-y: auto;
  background: white;
  -webkit-transition: all 0.5s ease;
  -moz-transition: all 0.5s ease;
  -o-transition: all 0.5s ease;
  transition: all 0.5s ease;
}

.sidebar-nav {
  position: absolute;
  top: 0;
  width: 100px;
  margin: 0;
  padding: 0;
  list-style: none;
  margin-top: 2px;
}

.sidebar-nav li {
  text-indent: 15px;
  line-height: 40px;
}

.sidebar-nav li a {
  display: block;
  text-decoration: none;
  color: #999999;
}

.sidebar-nav li a:hover {
  color: rgb(5, 25, 58);
  background: black;
  width: 8%;
}

@media (min-width: 768px) {
  #wrapper {
    padding-left: 100px;
  }
  .fixed-brand {
    width: 100px;
  }
  #wrapper.toggled {
    padding-left: 0;
  }
  #sidebar-wrapper {
    width: 100px;
  }
}

.next-button {
  margin-top: 10%;
  padding-top: 1%;
  padding-bottom: 1%;
  text-align: center;
}

.container {
  padding: 4%;
  padding-right: 4%;
}

.rounded-button {
  border-radius: 12px;
}

.surveyDes h1 {
  font-weight: 800;
}
.surveyDes h5 {
  font-weight: 700;
}

.surveyContent {
  margin-top: 50px;
}
.surveytitle {
  margin-top: 1em;
  margin-bottom: 1em;
  font-size: large;
}
.logo {
  margin-top: 3em;
}
.button .el-button {
  background-color: orange;
  border-color: orange;
  width: 100px;
}
.button .el-button:hover {
  background-color: rgba(255, 166, 0, 0.815);
  border-color: orange;
}
.button {
  text-align: center;
  margin-top: 3em;
}
</style>
