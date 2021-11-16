<template>
  <div>
    <div id="loginWrapper">

      <div id="logo">
        <div
          v-for="(t, index) in text"
          :key="index"
          class="item"
          :style="{animationDelay: index*150+'ms'}"
          v-text="t"
          />
          <div><img class="indexImage" src="@/assets/index.png" /></div>
          <br>
          
          <div><p class="etc">SSURBAR로 새로운 설문지를 만들어보세요.</p></div>

          
      </div>
      <div id="container">
        <h1>Login</h1>
        <div id="formDetail">
          <div class="form__group field">
            <input
              v-model="loginForm.email"
              type="input"
              class="form__field"
              placeholder="Name"
              required
            />
            <label for="name" class="form__label">Email</label>
          </div>
          <br />
          <div class="form__group field">
            <input
              v-model="loginForm.password"
              type="password"
              class="form__field"
              placeholder="Password"
              required
            />
            <label for="pass" class="form__label">Password</label>
          </div>
          <div class="buttonBox">
            <button class="btn-hover color-1" @click="submitForm()">
              Login
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      loginForm: {
        email: "ssafy@ssafy.com",
        password: "string",
      },
      text: 'Welcome To SSURBAR!'
    };
  },
  methods: {
    ...mapActions("user", ["onLogin"]),
    submitForm() {
      // console.log(this.loginForm);

      this.onLogin(this.loginForm);
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
  padding: 10%;
}
#formDetail {
  width: 95%;
  margin: 20% 0 0 0;
  text-align: center;
}
.buttonBox {
  margin-top: 40%;
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
  margin: 20px;
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

#logo{
  text-align: center;
  width:40%;
}
.item {
  margin-top:30%;
  display: inline-block;
  min-width: 0.3em;
  font-size: 4rem;
  animation: text-in .8s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
  color:white;
  font-weight: 700;
  text-shadow: 2px 2px 0px #3e4743, 
                  4px 4px 0px #000000, 

}

.indexImage{
  display: inline-block;
  width:50%;
}
.etc{
  color:lightgray;
}


</style>
