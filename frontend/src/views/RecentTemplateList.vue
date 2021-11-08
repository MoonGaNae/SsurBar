<template>
  <div id="wrapper">
    <div id="wrapper-div">
      <div id="container">
        <div class="page-title-div">
          <h1 style="padding-top: 3%; padding-bottom: 2%; font-size: 4rem">
            최근 사용 서식 조회
          </h1>
          <p>최근에 만들어진 서식순으로 보여드립니다.</p>
        </div>

        <hr />
        <div style="float: right; margin-bottom: 1%">
          <el-input v-model="search" size="mini" placeholder="Title to search">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
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
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      search: "",
    };
  },
  computed: {
    ...mapState("list", ["recentList"]),
  },
  methods: {
    ...mapActions("list", ["getRecentSurveyList", "setCategoryList"]),
    RecentTemplate(index, row) {
      console.log(row[index].surveyId);
      this.setCategoryList(row[index].surveyId);
      this.$router.push("/form/createform");
    },
  },
  created() {
    this.getRecentSurveyList();
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
#wrapper-div {
  background-position: center;
  background-color: white;
  margin-top: 3%;
  margin-left: 4%;
  margin-right: 4%;
  height: 90vh;
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
</style>
