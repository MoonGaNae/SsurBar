<template>
  <div>
    <div class="page-title-div" style="padding-top: 9%; padding-left: 5%">
      <div class="page-title-div-child">
        <h1>필터 항목 등록</h1>
      </div>
    </div>
    <hr style="width: 90%; margin-top: 3%; margin-left: 5%" />
    <button
      @click="endEditFilter()"
      class="custom-btn btn-5"
      style="margin-left: 86%"
    >
      <span>Next</span>
    </button>
    <div class="container">
      <div class="sub-title-div">
        <div>
          <h3 style="d-flex; text-align:left; font-size:2.5rem">
            설문 제목 :
            <span
              style="
                background: linear-gradient(
                  to top,
                  #ffe400 40%,
                  transparent 40%
                );
              "
            >
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
            style="width: 260%"
            placeholder="필터을 입력하세요."
          />
          <button
            @click="addCategory()"
            v-if="categoryInputState"
            class="custom-btn2 btn-5"
            style="width: 100%; margin-right: -3%"
          >
            입력 완료
          </button>
          <button
            v-if="categoryInputState"
            @click="cancelCategoryAdd()"
            class="custom-btn2 btn-5"
            style="width: 100%"
          >
            취소
          </button>
          <button
            class="custom-btn1 btn-5"
            @click="categoryInputState = true"
            v-if="!categoryInputState"
          >
            <span>필터 추가</span>
          </button>
        </div>
      </div>

      <div class="category-warning" v-text="categoryInputWarning"></div>
      <br />

      <div class="content-div">
        <div
          class="category-list el-card box-card is-always-shadow"
          style="
            width: 15vw;
            position: relative;
            clear: both;
            border-radius: 12px;
            margin-right: 10%;
          "
        >
          <h3 style="padding-top: 5%; padding-left: 10%; margin-bottom: 0">
            필터 목록
          </h3>
          <hr />
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
                    style="d-flex; text-align:left; padding-left:20%; font-size:1.6rem;"
                    @click="clickCategory(categoryIndex)"
                  >
                    {{ category.title }}
                  </div>

                  <div
                    @click="deleteCategory(categoryIndex)"
                    style="marign-left:10%"
                  >
                    <i class="el-icon-close"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="selectedCategoryIdx != null" class="question-list">
          <div
            class="question el-card box-card is-always-shadow"
            style="border-radius: 12px"
          >
            <div class="question-delete-btn-div"></div>
            <h4
              class="question-title"
              style="d-flex; text-align:left; font-size:1.5rem; margin-top:3%;"
            >
              <label style="margin-right: 2%; margin-left: 1%">필터 항목</label>
              <input
                class="question-title-input el-input__inner"
                style="d-flex; text-align:left; font-size:1.5rem;"
                type="text"
                v-model="categoryList[selectedCategoryIdx].title"
                placeholder="질문을 입력하세요"
              />
            </h4>
            <hr />
            <div class="answer-choices-list">
              <div
                class="choice"
                v-for="(choice, choiceIndex) in categoryList[
                  selectedCategoryIdx
                ].choiceList"
                :key="choiceIndex"
              >
                <div>
                  ✅
                  <input
                    type="text"
                    class="el-input__inner"
                    v-model="
                      categoryList[selectedCategoryIdx].choiceList[choiceIndex]
                    "
                    placeholder="새 항목"
                  />
                </div>
                <span
                  style="
                    width: 10%;
                    width: 0.6%;
                    position: absolute;
                    right: 40px;
                  "
                  @click="
                    deleteChoice(
                      categoryList[selectedCategoryIdx].choiceList,
                      choiceIndex
                    )
                  "
                >
                  <i class="el-icon-close"></i>
                </span>
              </div>
              <div class="choice-add-button-div">
                <button
                  class="custom-btn2 btn-6"
                  @click="
                    addChoice(categoryList[selectedCategoryIdx].choiceList)
                  "
                >
                  <span>보기 추가</span>
                </button>
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
    ...mapState("survey", ["surveyCreateType", "curCreateType"]),
    ...mapState("template", ["title"]),
  },
  methods: {
    ...mapActions("filterQuestion", ["setFilterQuestionList"]),
    deleteChoice: function (choiceList, index) {
      choiceList.splice(index, 1);
    },
    addChoice: function (choiceList) {
      choiceList.push("");
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
        choiceList: [],
      };

      let isExist = false;
      let length = this.categoryList.length;

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

      this.setFilterQuestionList(this.filterQuestionList);

      if (this.curCreateType == this.surveyCreateType.NEW) {
        this.$router.push({ path: "/question/questionedit" });
      } else {
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

.container {
  padding-bottom: 3%;
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
  padding-left: 10%;
  /* justify-content: space-between; */

  align-items: center;
}

.sub-title-div-buttons {
  display: flex;
  align-items: center;
  margin-left: 4%;
}

.sub-title-div-buttons button {
  width: 220%;
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

.question-list {
  width: 50vw;
  position: relative;
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
  width: 50%;
}

.category-title {
  display: flex;
  width: 100%;
  height: 7vh;
  justify-content: space-between;
  align-items: center;
  position: relative;
}

.category-title div:hover {
  font-weight: 900;
  cursor: pointer;
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
  margin-top: 2%;
  display: flex;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 5%;
}

button {
  margin: 20px;
}
.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 50px;
  padding: 10px 25px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 16px;
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

.custom-btn1 {
  width: 500px;
  height: 30px;
  color: #fff;
  border-radius: 50px;
  padding: 5px 20px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 10px;
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

.custom-btn2 {
  width: 100px;
  height: 30px;
  color: #fff;
  border-radius: 50px;
  padding: 5px 2px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  font-size: 10px;
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

.btn-6 {
  border: none;
  color: white;
  background-color: #36a72c;
}
.btn-6:hover {
  color: black;
  background: transparent;
  box-shadow: none;
}
.btn-6:before,
.btn-6:after {
  content: "";
  position: absolute;
  top: 0;
  right: 0;
  height: 2px;
  width: 0;
  background: #36a72c;
  box-shadow: -1px -1px 5px 0px #fff, 7px 7px 20px 0px #0003,
    4px 4px 5px 0px #0002;
  transition: 400ms ease all;
}
.btn-6:after {
  right: inherit;
  top: inherit;
  left: 0;
  bottom: 0;
}
.btn-6:hover:before,
.btn-6:hover:after {
  width: 100%;
  transition: 800ms ease all;
}
.page-title-div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 15vh;
}

.page-title-div-child > h1 {
  font-size: 4rem;
}

.contact-button {
  background-color: #ec4d4d;
  color: white;
  padding: 0.3% 0.3%;
  height: 10%;
  width: 10%;
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 0px 0px 6px #ec4d4d;
}
.contact-button,
.contact-button__icon {
  transition: all 0.5s ease-in-out;
}

.contact-button:hover {
  transform: scale(0.8);
  box-shadow: 0px 0px 0px 12px #ec6161;
  cursor: pointer;
}

.contact-button:hover .contact-button__icon {
  transform: scale(1.7);
}

.el-icon-close {
  cursor: pointer;
}
</style>
