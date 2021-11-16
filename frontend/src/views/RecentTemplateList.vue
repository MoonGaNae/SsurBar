<template>
  <div id="wrapper">
    <div class="page-title-div" style="padding-top:9%; padding-left:5%">
      <div class="page-title-div-child" >
        <h1>최근 사용 서식 조회</h1>
      </div>
    </div>
    <hr style="width: 90%; margin-top:3%; margin-left:5%;" />
    <el-input v-model="search" size="mini" placeholder="Title to search" style="margin-left:80%;width:15%">
      <el-button slot="append" icon="el-icon-search"></el-button>
    </el-input>
    <div id="wrapper-div">
      <div id="container">    
        <div style="float: right; margin-bottom: 1%">
        </div>
        <el-table
          :data="
            recentList.filter((data) => !search || data.title.includes(search))
          "
          style="width: 100%"
        >
          <el-table-column prop="title" label="설문 제목"> </el-table-column>
          <el-table-column prop="teamName" label="담당팀" width="180">
          </el-table-column>
          <el-table-column prop="creationTime" label="시작 날짜" width="180">
          </el-table-column>
          <el-table-column prop="endTime" label="종료 날짜" width="180">
          </el-table-column>
          <el-table-column label="편집하기" width="180">
            <template slot-scope="scope">
              <el-button
                @click.native.prevent="RecentTemplate(scope.$index, recentList)"
                type="info"
                plain
              >
                Click
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      search: "",
      questions: [],
      templateId : "",
    };
  },
  computed: {
    ...mapState("list", ["recentList"]),
  },
  methods: {
    ...mapActions("list", ["getRecentSurveyList", "setCategoryList"]),
    ...mapActions("question", ["setQuestionList"]),
    ...mapActions("template", ["setTemplateId"]),
    async RecentTemplate(index, row) {
      this.setCategoryList(row[index].surveyId);

      this.templateId = row[index].templateId;

      await this.getQuestionList(this.templateId);
      this.setTemplateId(this.templateId);
      
      this.$router.push("/form/createform");
    },

    getQuestionList(templateId) {
      axios.get("template/" + templateId + "/questions").then((res) => {
        // console.log(res);
        this.questions = res.data.questionList;

        // JSON 직렬화 시켜서 store에 저장
        let jsonList = new Array();
        this.questions.forEach((el) => {
          jsonList.push(JSON.stringify(el));
        });

        this.setQuestionList(jsonList);
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
  },
  created() {
    this.getRecentSurveyList();
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

.page-title-div-child > h1 {
  font-size: 4rem;
}


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
#wrapper-div {
  background-position: center;
  background-color: white;
  margin-top: 0%;
  margin-left: 4%;
  margin-right: 4%;
  border-radius: 60px 60px 0% 0%;
}
#intro {
  font-size: 2.5em;
  text-align: center;
  margin-top: 5%;
  margin-bottom: 3%;
}
#container {
  padding: 4%;
  padding-right: 4%;
}
#bottonBox {
  text-align: center;
  margin-bottom: 5%;
}
#bottonBox .surveyButton:hover {
  transform: scale(1.1);
}
#tab {
  margin-top: 2.5%;
}
.surveyButton {
  width: 25%;
  border-radius: 10px 10px 10px 10px;
  padding: 2% 5% 2% 5%;
  display: inline-block;
  margin-right: 5%;
  box-shadow: 2px 2px 2px 2px rgb(206, 206, 206);
  font-size: 0.5em;
  color: rgb(167, 167, 167);
}
.surveyButton > span > h5 {
  font-weight: 600;
  color: black;
}
.buttonImg {
  height: 50px;
  display: inline-block;
  margin-right: 5%;
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
</style>
