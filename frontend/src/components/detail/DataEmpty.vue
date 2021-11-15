<template>
  <div class="empty-wrapper">
    <div style="margin-bottom: 5vh">
      <img class="desImage" src="@/assets/sad.png" />
    </div>
    <div>
      <h1>아직 응답 데이터가 없습니다</h1>
    </div>
  </div>
</template>

<style scoped>
.empty-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.logo-div {
  height: 10vh;
  bottom: 1em;
}

.logo {
  width: 10%;
  position: absolute;
  bottom: 3em;
  right: 45%;
}
.desImage {
  width: 4vw;
}
</style>
