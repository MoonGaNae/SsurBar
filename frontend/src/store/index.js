import Vue from "vue";
import Vuex from "vuex";

import question from "@/store/modules/question.js";
import template from "@/store/modules/template.js";
import survey from "@/store/modules/survey.js";
import team from "@/store/modules/team.js";
import list from "@/store/modules/list.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { question, template, survey, team , list},
  state: {},
  mutations: {},
  actions: {},
});
