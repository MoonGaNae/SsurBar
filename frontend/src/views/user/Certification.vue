<template>
  <div id="wrapper">
    <div id="wrapper-div">
      <div id="container">
        <div class="page-title-div">
          <h1 style="padding-top: 3%; padding-bottom: 2%; font-size: 4rem">
            회원가입 승인
          </h1>
          <p>회원가입을 요청한 사용자들을 인증처리하는 페이지입니다.</p>
        </div>

        <hr />
        <!-- <div style="float: right; margin-bottom: 1%">
          <el-input v-model="search" size="mini" placeholder="Title to search">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div> -->
        <el-table
          :data="
            users
          "
          style="width: 100%"
        >
          <el-table-column prop="employeeNumber" label="사번"> </el-table-column>
          <el-table-column prop="name" label="이름" width="250">
          </el-table-column>
          <el-table-column prop="userType" label="상태" width="250">
          </el-table-column>
          <el-table-column prop="joinDate" label="회원가입 날짜" width="250">
          </el-table-column>
          <el-table-column label="승인하기" width="180">
            <template slot-scope="scope">
              <el-button
                @click.native.prevent="certificate(scope.$index, users)"
                type="success"
                plain
              >
                승인
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from "@/api/user.js";
import { mapState } from "vuex";
export default {
  data() {
    return {
      users: [],
    };
  },
  computed: {
    ...mapState("user", ["userInfo"]),
  },
  methods: {
    
    getUsers(){
        userApi.getUncertificateUsers()
        .then( (res) => {
            this.users = res.data.users;

            this.users.forEach(element => {
                element['joinDate'] = this.convertDate(element['joinDate']);
            });
        })
        .catch( (err) => {
            console.log(err);
        });
    },

    convertDate(joinDate){
        return new Date(Date.parse(joinDate)).toLocaleString();
    },
    certificate(index, row){
        userApi.certificateUser(row[index].userId)
            .then(() => {
                this.getUsers();
            })
            .catch( (err) => {
                console.log(err);
            });

    },
  },
  created() {
    if(this.userInfo == null || this.userInfo.userType != "ADMIN"){
        this.$router.push({ name: "Login" });
    }
    this.getUsers();
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
