<template>
  <div id="wrapper">
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
        <div class="container">
          <div class="page-title-div">
            <h1 style="padding-top: 3%; padding-left: 4%; font-size: 4rem">
              문항 편집
            </h1>
            <button
              @click="endEditSurvey()"
              class="next-button yellow-button rounded-corner-button"
            >
              Next
            </button>
          </div>
          <hr style="width: 100%" />
          <div class="sub-title-div">
            <div>
              <h3 style="d-flex; text-align:left; font-size:2.5rem">
                업무 만족도 조사
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
                class="rounded-corner-button white-button category-input-btn"
              >
                입력 완료
              </button>
              <button
                v-if="categoryInputState"
                @click="cancelCategoryAdd()"
                class="rounded-corner-button white-button category-cancel-btn"
              >
                취소
              </button>
              <button
                class="rounded-corner-button white-button category-add-btn"
                @click="categoryInputState = true"
                v-if="!categoryInputState"
              >
                카테고리 추가
              </button>
              <!-- <button class="rounded-corner-button while-button">질문 은행</button> -->
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
                <div
                  class="category-title-div"
                  @click="clickCategory(category)"
                >
                  <div class="category-title">
                    <div style="d-flex; text-align:left; font-size:2.5rem">
                      {{ category.title }}
                    </div>
                    <div class="category-arrow">
                      <i
                        v-if="category.isSelected"
                        class="el-icon-arrow-down"
                      ></i>
                      <i v-else class="el-icon-arrow-left"></i>
                    </div>
                  </div>
                  <div class="category-delete-div">
                    <button
                      class="
                        category-delete-btn
                        rounded-corner-button
                        red-button
                        el-button--danger
                      "
                      @click="deleteCategory(categoryIndex)"
                    >
                      카테고리 삭제
                    </button>
                  </div>
                </div>
                <div class="question-list" v-if="category.isSelected">
                  <div
                    class="question el-card box-card is-always-shadow"
                    v-for="(question, questionIndex) in category.questionList"
                    :key="questionIndex"
                  >
                    <div class="question-delete-btn-div">
                      <button
                        class="
                          rounded-corner-button
                          red-button
                          el-button--danger
                        "
                        @click="
                          deleteQuestion(category.questionList, questionIndex)
                        "
                      >
                        문제 삭제
                      </button>
                    </div>
                    <h4
                      class="question-title"
                      style="d-flex; text-align:left; font-size:2rem"
                    >
                      Q{{ questionIndex + 1 }}.
                      <input
                        class="question-title-input el-input__inner"
                        style="d-flex; text-align:left; font-size:1.5rem"
                        type="text"
                        v-model="
                          categoryList[categoryIndex].questionList[
                            questionIndex
                          ].title
                        "
                      />
                    </h4>
                    <div class="answer-choices-list">
                      <div
                        class="choice"
                        v-for="(choice, choiceIndex) in question.choiceList"
                        :key="choiceIndex"
                      >
                        <div>
                          {{ choiceIndex + 1 }}.
                          <input
                            type="text"
                            class="el-input__inner"
                            v-model="
                              categoryList[categoryIndex].questionList[
                                questionIndex
                              ].choiceList[choiceIndex]
                            "
                          />
                        </div>
                        <button
                          class="
                            el-button el-button--danger
                            is-circle
                            el-button--mini
                          "
                          @click="
                            deleteChoice(question.choiceList, choiceIndex)
                          "
                        >
                          <i class="el-icon-minus"></i>
                        </button>
                      </div>
                      <div class="choice-add-button-div">
                        <button
                          class="rounded-corner-button green-button"
                          @click="addChoice(question.choiceList)"
                        >
                          보기 추가
                        </button>
                      </div>
                    </div>
                  </div>
                  <button
                    class="rounded-corner-button green-button"
                    @click="addQuestion(category.questionList)"
                  >
                    문제 추가
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "QuestionEdit",
  data() {
    return {
      categoryInputState: false,
      categoryInput: "",
      categoryInputWarning: "",
      categoryNameList: [],
      questionList: [],
      categoryList: [
        {
          title: "카테고리1",
          isSelected: false,
          questionList: [
            {
              title: "귀하의 직무 만족도는 얼마입니까?",
              choiceList: ["매우 불만족", "보통", "매우 만족"],
            },
            {
              title: "귀하의 급여에 만족합니까?",
              choiceList: ["매우 불만족", "불만족", "조금 불만족"],
            },
          ],
        },
      ],
    };
  },
  methods: {
    ...mapActions("question", ["setQuestionList", "setCategoryList"]),
    deleteChoice: function (choiceList, index) {
      choiceList.splice(index, 1);
    },
    addChoice: function (choiceList) {
      choiceList.push("새 항목");
    },
    addQuestion: function (questionList) {
      let question = {
        title: "새 문제",
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
      this.questionList = [];

      this.categoryList.forEach((el) => {
        this.categoryNameList.push(el.title);

        el.questionList.forEach((question, index) => {
          let content = "{ ";

          question.choiceList.forEach((choice, index) => {
            content += `"${index + 1}":"${choice},`;
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
        path: `/test`,
      });
    },
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
}

.page-title-div h1 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0;
}

.next-button {
  margin-top: 10%;
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

.container {
  padding: 4%;
  padding-right: 4%;
}

.question-list {
  padding-top: 2%;
  padding-right: 0;
}

.question-title {
  display: flex;
  align-items: center;
  margin: 0;
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
  justify-content: space-between;
  align-items: center;
  margin: 2%;
}

.question-title {
  margin-left: 1%;
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
  width: 85%;
  justify-content: space-between;
  align-items: center;
  border-color: #9cbbff;
  border-style: solid;
  color: #9cbbff;
  border-radius: 12px;
}

.category-title:hover {
  background-color: white;
  filter: brightness(90%);
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
  margin-bottom: 2%;
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
  width: 70%;
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
  display: flex;
  justify-content: right;
  margin: 1%;
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
  width: 10%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.category-delete-div button {
  width: 100%;
}
</style>
