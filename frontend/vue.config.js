module.exports = {
  outputDir: "../backend/src/main/resources/dist",
  // 개발 서버 설정
  devServer: {
    port: 8081,
    // 프록시 설정
    proxy: {
      // 프록시 요청을 보낼 api의 시작 부분
      "/api/v1": {
        // 프록시 요청을 보낼 서버의 주소
        target: "http://localhost:8080/",
        changeOrigin: true,
      },
    },
  },
};
