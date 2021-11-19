<template>
  <div class="surveyWrapper">
    <div class="description">
      <div style="margin-bottom:5vh;"><img class="desImage" src="@/assets/sad.png" /></div>
      <h1>이미 종료된 설문입니다.</h1>
      <div class="logo-div" style="text-align: center">
        <img class="logo" src="@/assets/biglogo1.png" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.surveyWrapper {
  background-color: navy;
  min-height: 100vh;
}
.description {
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
  margin-left: 25vw;
  margin-right: 25vw;
  padding-top: 25vh;
  text-align: center;
  background-color: white;
}
.logo-div {
  height: 10vh;
  /* width: 20%; */
  /* position: absolute; */
  bottom: 1em;
  /* padding-right: 2em; */
  /* width: 100%; */
  /* right: 40%; */
}

.logo {
  /* margin-top: 3em; */
  width: 10%;
  position: absolute;
  bottom: 3em;
  right: 45%;
}
.desImage{
  width :4vw;
}
@media (max-width: 768px) {
  .surveyWrapper {
    min-height: 100vh;
  }
  .description {
    width: 100%;
    margin: 0px;
    text-align: center;
    background-color: white;
    position: relative;
  }
  .logo{
    width :30%;
    position: absolute;
    bottom: 3em;
    right: 35%;
  }
  .desImage{
    width :15vw;
  }
}
</style>
