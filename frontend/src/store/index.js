import Vue from "vue";
import Vuex from "vuex";

import question from "@/store/modules/question.js";
import team from "@/store/modules/team.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { 
    question,
    team,
   },
  state: {},
  mutations: {},
  actions: {},
});
