import Vue from "vue";
import Vuex from "vuex";

import question from "@/store/modules/question.js";
import template from "@/store/modules/template.js";
import survey from "@/store/modules/survey.js";
import team from "@/store/modules/team.js";
import filterQuestion from "@/store/modules/filterQuestion.js";

Vue.use(Vuex);

Vue.prototype.$mainUrl = "http://localhost:8080/";

export default new Vuex.Store({
  modules: { question, template, survey, team, filterQuestion },
  state: {},
  mutations: {},
  actions: {},
});
