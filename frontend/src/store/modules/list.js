import mainApi from "@/api/list.js";

const state = {
  surveyList: [],
  recentList: [],
  editSurveyId: "",
  selectedTab: 0,
};

const mutations = {
  SET_ONGOING_SURVEY_LIST: (state, payload) => {
    state.surveyList = payload;
  },
  SET_RECENT_SURVEY_LIST: (state, payload) => {
    state.recentList = payload;
  },
  SET_RECENT_TEMPLATE_ID: (state, payload) => {
    state.editSurveyId = payload;
    console.log(state.editSurveyId);
  },
  SET_SELECTED_TAB: (state, payload) => {
    state.selectedTab = payload;
  },
};
const actions = {
  initListData({ commit }) {
    commit("SET_RECENT_TEMPLATE_ID", "");
  },
  getOngoingSurveyList({ commit }) {
    mainApi
      .getOngoingSurveyList()
      .then((res) => {
        console.log(res);
        commit("SET_ONGOING_SURVEY_LIST", res.data.surveyList);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  getRecentSurveyList({ commit }) {
    mainApi
      .getRecentSurveyList()
      .then((res) => {
        console.log(res);
        commit("SET_RECENT_SURVEY_LIST", res.data.surveyList);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  setCategoryList({ commit }, surveyId) {
    commit("SET_RECENT_TEMPLATE_ID", surveyId);
  },
  setSelectedTab({ commit }, selectedTab) {
    commit("SET_SELECTED_TAB", selectedTab);
  },
};

const getters = {
  getOngoingSurveyListState(state) {
    return state.surveyList;
  },
  getRecentSurveyListState(state) {
    return state.recentList;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
