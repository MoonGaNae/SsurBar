<template>
  <div>
    <div id="loginWrapper">
      <div id="logo">
        <div
          v-for="(t, index) in text"
          :key="index"
          class="item"
          :style="{ animationDelay: index * 150 + 'ms' }"
          v-text="t"
        />
        <div><img class="indexImage" src="@/assets/index.png" /></div>
        <br />

        <div>
          <p class="etc">
            임직원들의 설문진단을 위한 플랫폼.<br />
            지금 SSURBAR로 새로운 설문지를 만들어보세요.
          </p>
        </div>
      </div>
      <div id="container">
        <h1>Sign Up</h1>
        <div id="formDetail">
          <div class="form__group field">
            <input
              v-model="signUpForm.name"
              type="input"
              class="form__field"
              placeholder="Name"
              required
            />
            <label for="name" class="form__label">Name</label>
          </div>
          <div class="form__group field">
            <input
              v-model="signUpForm.email"
              type="input"
              class="form__field"
              placeholder="Email"
              required
            />
            <label for="Email" class="form__label">Email</label>
          </div>
          <div class="form__group field">
            <input
              v-model="signUpForm.password"
              type="password"
              class="form__field"
              placeholder="Password"
              required
            />
            <label for="Password" class="form__label">Password</label>
          </div>
          <div class="form__group field">
            <input
              v-model="signUpForm.passwordCheck"
              type="password"
              class="form__field"
              placeholder="PasswordCheck"
              required
            />
            <label for="PasswordCheck" class="form__label"
              >Password Check</label
            >
          </div>
          <div class="form__group field">
            <input
              v-model="signUpForm.employeeNumber"
              type="input"
              class="form__field"
              placeholder="EmployeeNumber"
              required
            />
            <label for="EmployeeNumber" class="form__label"
              >Employee Number</label
            >
          </div>
          <div v-if="isWarning" v-text="warningText" class="warning-text"></div>
          <div class="buttonBox">
            <button class="btn-hover color-1" @click="submitSignUp()">
              Sign Up
            </button>
          </div>
          <div class="login-div">
            <span @click="moveToLogin()">login</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from "@/api/user.js";

export default {
  data() {
    return {
      signUpForm: {
        name: "",
        employeeNumber: "",
        email: "",
        password: "",
        passwordCheck: "",
      },
      isWarning: false,
      warningText: "",
      isPossible: false,
      text: "Welcome To SSURBAR!",
    };
  },
  methods: {
    submitSignUp() {
      if (
        this.signUpForm.name.length == 0 ||
        this.signUpForm.employeeNumber.length == 0 ||
        this.signUpForm.email.length == 0 ||
        this.signUpForm.password.length == 0 ||
        this.signUpForm.passwordCheck.length == 0
      ) {
        this.warningText = "모든 값을 입력해주세요";
        this.isWarning = true;
        return;
      }

      let nameReg = /[~!@#$%^&*()_+|<>?:{}ㄱ-ㅎㅏ-ㅣ]/;
      if (nameReg.test(this.signUpForm.name)) {
        this.warningText = "이름에 특수문자를 사용할 수 없습니다";
        this.isWarning = true;
        return;
      }

      if (this.signUpForm.password.length < 8) {
        this.warningText = "8자리 이상의 비밀번호를 입력해주세요";
        this.isWarning = true;
        return;
      }

      if (this.signUpForm.password != this.signUpForm.passwordCheck) {
        this.warningText = "비밀번호가 일치하지 않습니다";
        this.isWarning = true;
        return;
      }

      let emailReg = /^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/;

      if (!emailReg.test(this.signUpForm.email)) {
        this.warningText = "이메일 형식을 확인해주세요";
        this.isWarning = true;
        return;
      }

      if (nameReg.test(this.signUpForm.employeeNumber)) {
        this.warningText = "사원 번호에 특수문자를 사용할 수 없습니다";
        this.isWarning = true;
        return;
      }

      this.isWarning = false;

      userApi.signUp(this.signUpForm).then(() => {
        this.$router.push({ name: "Login" });
      });
    },
    moveToLogin() {
      this.$router.push({ name: "Login" });
    },
  },
};
</script>

<style lang="scss" scoped>
#loginWrapper {
  background-color: navy;
  width: 100%;
  height: 100vh;
  position: relative;
}
#container {
  height: 90vh;
  background-color: white;
  width: 50%;
  position: absolute;
  right: 10%;
  bottom: 0;
  border-radius: 10% 10% 0 0;
  text-align: center;
  padding: 7% 10% 10% 10%;
}
#formDetail {
  width: 95%;
  padding-top: 2vh;
  text-align: center;
}
.buttonBox {
  margin-top: 10%;
}

* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.btn-hover {
  width: 100%;
  font-size: 1.3em;
  font-weight: 600;
  color: #fff;
  cursor: pointer;
  height: 55px;
  text-align: center;
  border: none;
  background-size: 300% 100%;

  border-radius: 50px;
  moz-transition: all 0.4s ease-in-out;
  -o-transition: all 0.4s ease-in-out;
  -webkit-transition: all 0.4s ease-in-out;
  transition: all 0.4s ease-in-out;
}

.btn-hover:hover {
  background-position: 100% 0;
  moz-transition: all 0.4s ease-in-out;
  -o-transition: all 0.4s ease-in-out;
  -webkit-transition: all 0.4s ease-in-out;
  transition: all 0.4s ease-in-out;
}

.btn-hover:focus {
  outline: none;
}
.btn-hover.color-1 {
  background-image: linear-gradient(
    to right,
    #25aae1,
    #40e495,
    #30dd8a,
    #2bb673
  );
  box-shadow: 0 4px 15px 0 rgba(49, 196, 190, 0.75);
}

$primary: #11998e;
$secondary: #38ef7d;
$white: #fff;
$gray: #9b9b9b;
.form__group {
  position: relative;
  padding: 20px 0 0;
  margin-top: 10px;
  width: 100%;
}

.form__field {
  font-family: inherit;
  width: 100%;
  border: 0;
  border-bottom: 2px solid $gray;
  outline: 0;
  font-size: 1.5rem;
  color: black;
  padding: 7px 0;
  background: transparent;
  transition: border-color 0.2s;

  &::placeholder {
    color: transparent;
  }

  &:placeholder-shown ~ .form__label {
    font-size: 1.3rem;
    cursor: text;
    top: 20px;
  }
}

.form__label {
  position: absolute;
  top: 0;
  display: block;
  transition: 0.2s;
  font-size: 1rem;
  color: $gray;
}

.form__field:focus {
  ~ .form__label {
    position: absolute;
    top: 0;
    display: block;
    transition: 0.2s;
    font-size: 1rem;
    color: $primary;
    font-weight: 700;
  }
  padding-bottom: 6px;
  font-weight: 700;
  border-width: 3px;
  border-image: linear-gradient(to right, $primary, $secondary);
  border-image-slice: 1;
}
/* reset input */
.form__field {
  &:required,
  &:invalid {
    box-shadow: none;
  }
}

@keyframes text-in {
  0% {
    transform: translate(0, -20px);
    opacity: 0;
  }
}

#logo {
  text-align: center;
  width: 40%;
}
.item {
  margin-top: 30%;
  display: inline-block;
  min-width: 0.3em;
  font-size: 3rem;
  animation: text-in 0.8s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  color: white;
  font-weight: 700;
  text-shadow: 2px 2px 0px #3e4743, 4px 4px 0px #000000;
}

.indexImage {
  display: inline-block;
  width: 50%;
}
.etc {
  color: lightgray;
}

.warning-text {
  color: red;
  padding-top: 30px;
}

.login-div {
  display: flex;
  justify-content: center;
  width: 100%;
  color: #9b9b9b;
  padding-top: 30px;
}

.login-div > span:hover {
  cursor: pointer;
  font-weight: 600;
  color: #000000;
}
</style>
