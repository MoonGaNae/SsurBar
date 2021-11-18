<template>
  <div>
    <div class="page-title-div" style="padding-top: 9%; padding-left: 5%">
      <div class="page-title-div-child">
        <h1>문항 편집</h1>
      </div>
    </div>
    <hr style="width: 90%; margin-top: 3%; margin-left: 5%" />
    <button
      @click="endEditSurvey()"
      class="custom-btn btn-5"
      style="margin-left: 86%; margin-top: 1%"
    >
      <i class="fas fa-arrow-right"></i>
    </button>
    <div class="container">
      <div class="sub-title-div">
        <div>
          <h3 style="d-flex; text-align:left; font-size:2.5rem">
            설문 제목 :
            <span style="background: linear-gradient(to top, rgb(255, 228, 0) 40%, transparent 40%);">
              {{ this.title }}
            </span>
          </h3>
        </div>
        <div class="sub-title-div-buttons">
          <input
            class="el-input__inner"
            type="text"
            v-model="categoryInput"
            v-if="categoryInputState"
          />
          <button
            @click="addCategory()"
            v-if="categoryInputState"
            class="custom-btn2 btn-5"
          >
            추가
          </button>
          <button
            v-if="categoryInputState"
            @click="cancelCategoryAdd()"
            class="custom-btn2 btn-1"
            style="margin-left: 2%"
          >
            취소
          </button>
          <button
            class="custom-btn2 btn-5"
            @click="categoryInputState = true"
            v-if="!categoryInputState"
          >
            카테고리 생성
          </button>
        </div>
      </div>
      <div class="category-warning" v-text="categoryInputWarning"></div>

      <div class="category-list">
        <div
          class="category-div"
          v-for="(category, categoryIndex) in categoryList"
          :key="categoryIndex"
        >
          <div class="category" :id="'category' + categoryIndex">
            <div class="category-title-div" @click="clickCategory(category)">
              <div class="category-title">
                <div
                  style="d-flex; text-align:left; margin-left:3%; font-size:2rem"
                >
                  카테고리명: {{ category.title }}
                </div>
                <div class="category-arrow">
                  <i v-if="category.isSelected" class="el-icon-arrow-down"></i>
                  <i v-else class="el-icon-arrow-left"></i>
                </div>
              </div>
              <div class="category-delete-div">
                <el-button
                  type="danger"
                  @click="deleteCategory(categoryIndex)"
                  icon="el-icon-delete"
                  circle
                >
                </el-button>
              </div>
            </div>
            <div class="question-list" v-if="category.isSelected">
              <div
                class="question el-card box-card is-always-shadow"
                v-for="(question, questionIndex) in category.questionList"
                :key="questionIndex"
              >
                <div class="question-delete-btn-div">
                  <el-button
                    circle
                    icon="el-icon-close"
                    @click="
                      deleteQuestion(category.questionList, questionIndex)
                    "
                  >
                  </el-button>
                </div>
                <h4
                  class="question-title"
                  style="d-flex; text-align:left; font-size:2rem"
                >
                  <div class="question-q">Q{{ questionIndex + 1 }}.</div>
                  <input
                    class="question-title-input el-input__inner"
                    style="d-flex; text-align:left; font-size:1.5rem"
                    placeholder="새 문제"
                    type="text"
                    v-model="
                      categoryList[categoryIndex].questionList[questionIndex]
                        .title
                    "
                  />
                </h4>
                <div class="answer-choices-list">
                  <div
                    class="choice"
                    v-for="(choice, choiceIndex) in question.choiceList"
                    :key="choiceIndex"
                  >
                    <!-- <div> -->
                    <div class="choice-number">{{ choiceIndex + 1 }}.</div>
                    <input
                      type="text"
                      placeholder="새 문항"
                      class="el-input__inner"
                      v-model="
                        categoryList[categoryIndex].questionList[questionIndex]
                          .choiceList[choiceIndex]
                      "
                    />
                    <!-- </div> -->
                    <button
                      class="
                        el-button el-button--danger
                        is-circle
                        el-button--mini
                      "
                      @click="deleteChoice(question.choiceList, choiceIndex)"
                    >
                      <i class="el-icon-minus"></i>
                    </button>
                  </div>
                  <div class="choice-add-button-div">
                    <el-button
                      class="choice-add-btn"
                      icon="el-icon-plus"
                      style="width: 100%; font-size: 1rem"
                      @click="addChoice(question.choiceList)"
                    >
                    </el-button>
                  </div>
                </div>
              </div>
              <el-button
                type="success"
                plain
                icon="el-icon-document-add"
                @click="addQuestion(category.questionList)"
              >
                문제 추가
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "QuestionEdit",
  data() {
    return {
      categoryInputState: false,
      categoryInput: "",
      categoryInputWarning: "",
      categoryNameList: [],
      questionList: [],
      categoryList: [],
    };
  },
  computed: {
    ...mapState("template", ["title"]),
  },
  methods: {
    ...mapActions("question", ["setQuestionList", "setCategoryList"]),
    deleteChoice: function (choiceList, index) {
      choiceList.splice(index, 1);
    },
    addChoice: function (choiceList) {
      choiceList.push("");
    },
    addQuestion: function (questionList) {
      let question = {
        choiceList: [],
      };
      questionList.push(question);
    },
    deleteQuestion: function (questionList, questionIndex) {
      questionList.splice(questionIndex, 1);
    },
    clickCategory: function (category) {
      category.isSelected = !category.isSelected;
    },
    addCategory: function () {
      let category = {
        title: this.categoryInput,
        isSelected: false,
        questionList: [],
      };

      let isExist = false;
      let length = this.categoryList.length;

      console.log(length);

      for (let i = 0; i < length; i++) {
        if (this.categoryList[i].title == this.categoryInput) {
          isExist = true;
          break;
        }
      }

      if (isExist) {
        this.categoryInputWarning = "이미 존재하는 카테고리입니다";
      } else {
        this.categoryList.push(category);
        this.categoryInput = "";
        this.categoryInputWarning = "";
        this.categoryInputState = false;
      }
    },
    cancelCategoryAdd: function () {
      this.categoryInputState = false;
      this.categoryInput = "";
      this.categoryInputWarning = "";
    },
    testClick: function (list) {
      console.log(list);
    },
    deleteCategory: function (categoryIndex) {
      this.categoryList.splice(categoryIndex, 1);
    },
    endEditSurvey: function () {
      if (this.isValid()) {
        this.questionList = [];

        this.categoryList.forEach((el) => {
          this.categoryNameList.push(el.title);

          el.questionList.forEach((question, index) => {
            let content = "{ ";

            question.choiceList.forEach((choice, index) => {
              content += `"${index + 1}":"${choice}",`;
              // content += '"' + index + '":' + choice + ",";
            });

            content = content.slice(0, content.length - 1) + "}";

            // console.log(content);

            let questionInfo = {
              title: question.title,
              isEssential: false,
              number: index + 1,
              questionType: 201,
              content: content,
              categoryName: el.title,
            };

            this.questionList.push(JSON.stringify(questionInfo));
          });
        });

        this.setCategoryList(this.categoryNameList);
        this.setQuestionList(this.questionList);

        this.$router.push({
          name: `CreatePreview`,
        });
      } else {
        this.$fire({
          title: "응답 실패",
          text: "카테고리 및 문제항목 입력은 필수입니다.",
          type: "error",
        });
      }
    },
    isValid() {
      if (this.categoryList.length == 0) {
        return false;
      } else {
        for (var i = 0; i < this.categoryList.length; i++) {
          if (this.categoryList[i].questionList.length == 0) {
            return false;
          } else {
            for (var j = 0; j < this.categoryList[i].questionList.length; j++) {
              if (this.categoryList[i].questionList[j].choiceList.length == 0) {
                return false;
              }
            }
          }
        }
        return true;
      }
    },
  },
};
</script>

<style scoped>
.nav-pills > li > a {
  border-radius: 0;
}

.page-title-div-child > h1 {
  font-size: 4rem;
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
.form-checkbox {
  position: relative;
  margin-top: 2.25rem;
  margin-bottom: 2.25rem;
  text-align: left;
}
.form-checkbox-inline .form-checkbox-label {
  display: inline-block;
  margin-right: 1rem;
}

.form-checkbox-legend {
  margin: 0 0 0.125rem 0;
  font-weight: 500;
  font-size: 1rem;
  color: #333;
}

.form-checkbox-label {
  position: relative;
  cursor: pointer;
  padding-left: 1.5rem;
  text-align: left;
  color: #333;
  display: block;
  margin-bottom: 0.5rem;
}

.form-checkbox-label:hover i {
  color: #337ab7;
}

.form-checkbox-label span {
  display: block;
}

.form-checkbox-label input {
  width: auto;
  opacity: 0.0001;
  position: absolute;
  left: 0.25rem;
  top: 0.25rem;
  margin: 0;
  padding: 0;
}

.form-checkbox-button {
  position: absolute;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  display: block;
  color: #999;
  left: 0;
  top: 0.25rem;
  width: 1rem;
  height: 1rem;
  z-index: 0;
  border: 0.125rem solid currentColor;
  border-radius: 0.0625rem;
  transition: color 0.28s ease;
  will-change: color;
}

.form-checkbox-button::before,
.form-checkbox-button::after {
  position: absolute;
  height: 0;
  width: 0.2rem;
  background-color: #337ab7;
  display: block;
  transform-origin: left top;
  border-radius: 0.25rem;
  content: "";
  transition: opacity 0.28s ease, height 0s linear 0.28s;
  opacity: 0;
  will-change: opacity, height;
}

.form-checkbox-button::before {
  top: 0.65rem;
  left: 0.38rem;
  transform: rotate(-135deg);
  box-shadow: 0 0 0 0.0625rem #fff;
}

.form-checkbox-button::after {
  top: 0.3rem;
  left: 0;
  transform: rotate(-45deg);
}

.form-checkbox-field:checked ~ .form-checkbox-button {
  color: #337ab7;
}

.form-checkbox-field:checked ~ .form-checkbox-button::after,
.form-checkbox-field:checked ~ .form-checkbox-button::before {
  opacity: 1;
  transition: height 0.28s ease;
}

.form-checkbox-field:checked ~ .form-checkbox-button::after {
  height: 0.5rem;
}

.form-checkbox-field:checked ~ .form-checkbox-button::before {
  height: 1.2rem;
  transition-delay: 0.28s;
}

.page-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 15vh;
}

/* .page-title-div h1 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0;
} */

.next-button {
  margin-top: 10%;
  margin-bottom: 4%;
  padding-top: 1%;
  padding-bottom: 1%;
  text-align: center;
  /* height: 20%; */
  width: 10%;
}

.sub-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.sub-title-div-buttons {
  display: flex;
  align-items: center;
  margin-top: 2%;
  width: 30%;
  height: 120%;
}

.sub-title-div-buttons button {
  width: 30%;
  margin-left: 2%;
}

.sub-title-div-buttons .el-input__inner {
  height: 100%;
}

.category-add-btn {
  min-width: 100px;
}

.category-input-btn {
  min-width: 80px;
}

.category-cancel-btn {
  min-width: 40px;
}

/* .container {
  padding: 4%;
  padding-right: 4%;
} */

.question-list {
  padding-top: 5%;
  padding-right: 0;
}

.question-list div {
  border-radius: 12px;
}
.question-title {
  display: flex;
  align-items: center;
  margin: 0;
  margin-bottom: 2vh;
}

.question-title input {
  width: 80%;
}

.sub-title-div div h3 {
  margin: 0;
}

.question {
  margin-bottom: 2%;
}

.choice {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 1vh;
  /* margin: 2%; */
  /* padding-left: 10%; */
}

.question-title {
  padding-left: 3%;
  padding-right: 3%;
}

.yellow-button {
  background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white;
}

.yellow-button:hover {
  /* background-color: white;
  border-style: solid;
  border-color: #ffa724;
  color: #ffa724; */
  filter: brightness(115%);
}

.white-button {
  background-color: white;
  border-style: solid;
  border-color: #ffa724;
  color: #ffa724;
}

.white-button:hover {
  /* background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white; */
  filter: brightness(90%);
}

.answer-choices-list {
  padding-left: 3%;
  padding-right: 3%;
}

.red-button {
  background-color: #f56c6c;
  border-style: solid;
  border-color: #f56c6c;
  color: white;
}

.red-button:hover {
  /* background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white; */
  /* filter: brightness(90%); */
}

.rounded-corner-button {
  border-radius: 12px;
}

.round-button {
  border-radius: 50%;
}

/* .question {
  border: 3px;
  border-style: solid;
} */

.category-title-div {
  display: flex;
  background-color: white;
  justify-content: space-between;
  /* margin-top: 5%; */
  /* padding-left: 2%; */
  width: 100%;
  /* border-radius: 12px;
  border-color: #9cbbff;
  border-style: solid;
  color: #9cbbff; */
}

.category-title-div:hover {
  cursor: pointer;
}

.category-title {
  display: flex;
  width: 95%;
  justify-content: space-between;
  align-items: center;
  border-radius: 50px;
  background-color: rgb(50, 50, 168);
  color: white;
  /* background: linear-gradient(to left,rgb(144, 114, 179), rgb(120, 134, 170)); */
}

.category-title:hover {
  background: linear-gradient(to left, rgb(128, 150, 248), rgb(55, 72, 116));
  /* filter: brightness(90%); */
}

.category-warning {
  text-align: right;
  color: #f56c6c;
}

.green-button {
  background-color: #67c23a;
  border-color: #67c23a;
  border-style: solid;
  color: white;
}

.green-button:hover {
  filter: brightness(110%);
}

button:hover {
  cursor: pointer;
}

.category-div {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 5%;
  margin-bottom: 3%;
}

.category-list {
  margin-top: 5%;
  margin-bottom: 5%;
}

.category {
  width: 100%;
}

.question-delete-btn-div {
  display: flex;
  justify-content: right;
  margin-top: 1%;
  margin-right: 1%;
}

.question-title-input {
  width: 84% !important;
}

.question-q {
  width: 10%;
}

.choice div {
  width: 70%;
  display: flex;
  align-items: center;
}

.choice div input {
  margin-left: 2%;
}

.choice-add-button-div {
  margin-bottom: 4vh;
}

.choice-add-button-div > button {
  margin-left: 14%;
  width: 80% !important;
}

.choice-add-btn {
  width: 100%;
}

.category-arrow {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 5%;
}

.category-arrow i {
  /* width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center; */
  font-size: 200%;
}

.category-delete-btn {
  height: 100%;
}

.category-delete-div {
  display: flex;
  justify-content: center;
  align-items: center;
}

.category-delete-div button {
  width: 100%;
}

.custom-btn2 {
  width: 100px;
  height: 30px;
  color: #fff;
  border-radius: 50px;
  padding: 5px 2px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 14px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 1px 1px 1px 0px rgba(255, 255, 255, 0.5),
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

.btn-1 {
  border: none;
  color: white;
  background-color: #c73030;
}
.btn-1:hover {
  color: black;
  background: transparent;
  box-shadow: none;
}
.btn-1:before,
.btn-1:after {
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #c73030;
  box-shadow: -1px -1px 5px 0px #fff, 7px 7px 20px 0px #0003,
    4px 4px 5px 0px #0002;
  transition: 400ms ease all;
}
.btn-1:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}
.btn-1:hover:before,
.btn-1:hover:after {
  width: 100%;
  transition: 800ms ease all;
}

.choice-number {
  width: 12% !important;
  justify-content: right;
  /* margin-right: 2%; */
}
/* 
.choice > div {
  width: 100%;
} */

.choice > input {
  margin-left: 2%;
  width: 80%;
}

.choice > button {
  margin-left: 2%;
}
</style>
