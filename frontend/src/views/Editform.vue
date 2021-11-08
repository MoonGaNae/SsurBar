<template>
  <div id="wrapper">
    <div id="sidebar-wrapper">
      <ul class="sidebar-nav nav-pills nav-stacked" id="menu">
        <li style="margin-top: 25vh; margin-left: 5%">
          <a href="#"
            ><i class="fas fa-home fa-2x" style="margin-top: 1vh"></i
          ></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"
            ><i class="fas fa-plus-square fa-2x" style="margin-top: 1vh"></i
          ></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"
            ><i class="fas fa-chart-bar fa-2x" style="margin-top: 1vh"></i
          ></a>
        </li>

        <li style="margin-top: 10vh; margin-left: 10%">
          <a href="#"
            ><i class="fas fa-cog fa-2x" style="margin-top: 1vh"></i
          ></a>
        </li>
      </ul>
    </div>
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
              필터 항목 등록
            </h1>
            <button
              @click="endEditFilter()"
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
                필터 추가
              </button>
            </div>
          </div>

          <div class="category-warning" v-text="categoryInputWarning"></div>
          <br />

          <div class="content-div">
            <div
              class="category-list el-card box-card is-always-shadow"
              style="width: 10vw; margin-top: 2%; padding-left: 1%"
            >
              <div
                class="category-div"
                v-for="(category, categoryIndex) in categoryList"
                :key="categoryIndex"
                style="width: 40vw"
              >
                <div
                  class="category d-flex justify-content-between"
                  :id="'category' + categoryIndex"
                >
                  <div class="category-title-div">
                    <div class="category-title">
                      <div
                        style="d-flex; text-align:left; font-size:2.3rem;"
                        @click="clickCategory(categoryIndex)"
                      >
                        {{ category.title }}
                      </div>

                      <div class="category-delete-div">
                        <button
                          class="el-button el-button--danger is-circle"
                          style="width: 2vw"
                          @click="deleteCategory(categoryIndex)"
                        >
                          <i class="el-icon-minus"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div v-if="selectedCategoryIdx != null" class="question-list">
              <div class="question el-card box-card is-always-shadow">
                <div class="question-delete-btn-div"></div>
                <h4
                  class="question-title"
                  style="d-flex; text-align:left; font-size:2rem"
                >
                  <input
                    class="question-title-input el-input__inner"
                    style="d-flex; text-align:left; font-size:1.5rem"
                    type="text"
                    v-model="categoryList[selectedCategoryIdx].title"
                    placeholder="질문을 입력하세요"
                  />
                </h4>
                <div class="answer-choices-list">
                  <div
                    class="choice"
                    v-for="(choice, choiceIndex) in categoryList[
                      selectedCategoryIdx
                    ].choiceList"
                    :key="choiceIndex"
                  >
                    <div>
                      <input
                        type="text"
                        class="el-input__inner"
                        v-model="
                          categoryList[selectedCategoryIdx].choiceList[
                            choiceIndex
                          ]
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
                        deleteChoice(
                          categoryList[selectedCategoryIdx].choiceList,
                          choiceIndex
                        )
                      "
                    >
                      <i class="el-icon-minus"></i>
                    </button>
                  </div>
                  <div class="choice-add-button-div">
                    <button
                      class="rounded-corner-button green-button"
                      @click="
                        addChoice(categoryList[selectedCategoryIdx].choiceList)
                      "
                    >
                      보기 추가
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script scoped>
import { mapActions, mapState } from "vuex";

export default {
  name: "FilterQuestionEdit", 
  data() {
    return {
      categoryInputState: false,
      categoryInput: "",
      categoryInputWarning: "",
      categoryNameList: [],
      filterQuestionList: [],
      selectedCategoryIdx: null,
      categoryList: [
        {
          title: "직무",
          choiceList: ["개발팀", "영업팀", "보안팀"],
        },
        {
          title: "성별",
          choiceList: ["여성", "남성"],
        },
        {
          title: "나이대",
          choiceList: ["20대", "30대", "40대", "50대"],
        },
      ],
    };
  },
  computed: {
    ...mapState('survey', ['surveyCreateType', 'curCreateType']),
  },
  methods: {
    ...mapActions("filterQuestion", ["setFilterQuestionList"]),
    deleteChoice: function (choiceList, index) {
      choiceList.splice(index, 1);
    },
    addChoice: function (choiceList) {
      choiceList.push("새 항목");
    },
    addQuestion: function (filterList) {
      let question = {
        title: "새 문제",
        choiceList: [],
      };
      filterList.push(question);
    },
    deleteQuestion: function (filterList, questionIndex) {
      filterList.splice(questionIndex, 1);
    },
    clickCategory: function (categoryIdx) {
      this.selectedCategoryIdx = categoryIdx;
    },
    addCategory: function () {
      let category = {
        title: this.categoryInput,
        isSelected: false,
        filterList: [],
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
        this.categoryInputWarning = "이미 존재하는 필터링입니다";
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
      if (this.selectedCategoryIdx == categoryIndex) {
        this.selectedCategoryIdx = null;
      }
      this.categoryList.splice(categoryIndex, 1);
    },
    endEditFilter: function () {
      this.categoryList.forEach((category, idx) => {
        let content = {};
        category.choiceList.forEach((choice, choiceIdx) => {
          content[choiceIdx + 1] = choice;
        });

        let jsonData = {
          content: JSON.stringify(content),
          number: idx + 1,
          title: category.title,
        };

        this.filterQuestionList.push(JSON.stringify(jsonData));
      });

      console.log(this.filterQuestionList);
      this.setFilterQuestionList(this.filterQuestionList);

      if(this.curCreateType == this.surveyCreateType.NEW){
        this.$router.push({ path: "/question/questionedit" });
      }else {
        this.$router.push({ path: "/test" });
      }
      
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
  width: 35vw;
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
  margin-left: -5vh;
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

.rounded-corner-button {
  border-radius: 12px;
}

.round-button {
  border-radius: 50%;
}

.choice-add-button {
  background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white;
}

.question-add-button {
  background-color: #ffa724;
  border-style: solid;
  border-color: #ffa724;
  color: white;
}

.category-title-div {
  display: flex;
  background-color: white;
  justify-content: space-between;
  /* padding-left: 2%; */
  width: 50%;

  /* border-radius: 12px;
  border-color: #9cbbff;
  border-style: solid;
  color: #9cbbff; */
}

.category-title {
  display: flex;
  width: 100%;
  height: 8vh;
  justify-content: space-between;
  align-items: center;
  color: #9cbbff;
  border-radius: 12px;
}

.category-title div:hover {
  color: rgb(5, 25, 58);
  cursor: pointer;
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
  width: 50%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 2%;
}

.category {
  width: 40%;
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

.content-div {
  display: flex;
  justify-content: space-between;
}
</style>
