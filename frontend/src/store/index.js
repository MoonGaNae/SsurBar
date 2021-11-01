import Vue from "vue";
import Vuex from "vuex";

import question from "@/store/modules/question.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { question },
  state: {},
  mutations: {},
  actions: {},
});
