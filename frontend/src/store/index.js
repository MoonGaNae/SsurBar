import Vue from "vue";
import Vuex from "vuex";

import question from "@/store/modules/question.js";
import template from "@/store/modules/template.js";
import survey from "@/store/modules/survey.js";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { question, template, survey },
  state: {},
  mutations: {},
  actions: {},
});
