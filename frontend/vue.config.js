module.exports = {
    css : {
      loaderOptions : {
        sass : {
          additionalData: `
            @import "@/assets/style/main.css";
          `
        }
      }
    }
  }