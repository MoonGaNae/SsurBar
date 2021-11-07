import mainApi from "@/api/analysis.js";

const state = {
  answerDataList: null,
  highestAverageList: null,
  highestStandardDeviationList: null,
  lowestAverageList: null,
  lowestStandardDeviationList: null,
};

const mutations = {
  SET_ANSWER_DATA_LIST: (state, answerDataList) => {
    state.answerDataList = answerDataList;
  },
  SET_HIGHEST_AVERAGE_LIST: (state, highestAverageList) => {
    state.highestAverageList = highestAverageList;
  },
  SET_HIGHEST_STANDARD_DEVIATION_LIST: (state, highestStandardDeviationList) => {
    state.highestStandardDeviationList = highestStandardDeviationList;
  },
  SET_LOWEST_AVERAGE_LIST: (state, lowestAverageList) => {
    state.lowestAverageList = lowestAverageList;
  },
  SET_LOWEST_STANDARD_DEVIATION_LIST: (state, lowestStandardDeviationList) => {
    state.lowestStandardDeviationList = lowestStandardDeviationList;
  },
};
const actions = {
  setAnswerData({ commit }, searchData) {
    mainApi
      .getAnswerData(searchData.surveyId, searchData.filterStr)
      .then((res) => {
        console.log(res);
        commit("SET_ANSWER_DATA_LIST", res.data.answerDataList);
        commit("SET_HIGHEST_AVERAGE_LIST", res.data.highestAverageList);
        commit("SET_HIGHEST_STANDARD_DEVIATION_LIST", res.data.highestStandardDeviationList);
        commit("SET_LOWEST_AVERAGE_LIST", res.data.lowestAverageList);
        commit("SET_LOWEST_STANDARD_DEVIATION_LIST", res.data.lowestStandardDeviationList);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};

const getters = {};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
