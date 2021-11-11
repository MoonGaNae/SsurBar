<template>
  <div class="surveyWrapper">
    <div class="description">
      <h1>이미 종료된 설문입니다.</h1>
      <div style="text-align: center">
        <img class="logo" src="@/assets/smalllogo.png" />
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
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
  margin-left: 25vw;
  margin-right: 25vw;
  padding-top: 30vh;
  text-align: center;
  background-color: white;
}

.logo {
  padding-bottom: 10vh;
  width: 40vw;
}
</style>
