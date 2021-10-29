import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false;
Vue.use(Element, { size: 'small', zIndex: 3000 });
Vue.use(BootstrapVue)

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
