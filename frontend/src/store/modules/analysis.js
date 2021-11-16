import mainApi from "@/api/analysis.js";

const state = {
  answerDataList: null,
  highestAverageList: null,
  highestStandardDeviationList: null,
  lowestAverageList: null,
  lowestStandardDeviationList: null,
  radarDataSets: null,
  radarLabels: null,
  barDataSets: null,
  barLabels: null,
  questionCount: null,
  comparisonLabels: null,
  comparisonDataSets: null,
  isLoading: true,
};

const mutations = {
  SET_ANSWER_DATA_LIST: (state, answerDataList) => {
    state.answerDataList = answerDataList;
  },
  SET_HIGHEST_AVERAGE_LIST: (state, highestAverageList) => {
    state.highestAverageList = highestAverageList;
  },
  SET_HIGHEST_STANDARD_DEVIATION_LIST: (
    state,
    highestStandardDeviationList
  ) => {
    state.highestStandardDeviationList = highestStandardDeviationList;
  },
  SET_LOWEST_AVERAGE_LIST: (state, lowestAverageList) => {
    state.lowestAverageList = lowestAverageList;
  },
  SET_LOWEST_STANDARD_DEVIATION_LIST: (state, lowestStandardDeviationList) => {
    state.lowestStandardDeviationList = lowestStandardDeviationList;
  },
  SET_RADAR_DATA_SETS: (state, radarDataSets) => {
    state.radarDataSets = radarDataSets;
  },
  SET_RADAR_LABELS: (state, radarLabels) => {
    state.radarLabels = radarLabels;
  },
  SET_BAR_DATA_SETS: (state, barDataSets) => {
    state.barDataSets = barDataSets;
  },
  SET_BAR_LABELS: (state, barLabels) => {
    state.barLabels = barLabels;
  },
  SET_QUESTION_COUNT: (state, questionCount) => {
    state.questionCount = questionCount;
  },
  SET_COMPARISON_DATA_SETS: (state, comparisonDataSets) => {
    state.comparisonDataSets = comparisonDataSets;
  },
  SET_COMPARISON_LABELS: (state, comparisonLabels) => {
    state.comparisonLabels = comparisonLabels;
  },
  SET_IS_LOADING: (state, isLoading) => {
    state.isLoading = isLoading;
  },
};
const actions = {
  initAnalysisData({ commit }) {
    commit("SET_ANSWER_DATA_LIST", null);
    commit("SET_HIGHEST_AVERAGE_LIST", null);
    commit("SET_HIGHEST_STANDARD_DEVIATION_LIST", null);
    commit("SET_LOWEST_AVERAGE_LIST", null);
    commit("SET_LOWEST_STANDARD_DEVIATION_LIST", null);
    commit("SET_RADAR_DATA_SETS", null);
    commit("SET_BAR_DATA_SETS", null);
    commit("SET_BAR_LABELS", null);
    commit("SET_QUESTION_COUNT", null);
    commit("SET_COMPARISON_DATA_SETS", null);
    commit("SET_COMPARISON_LABELS", null);
    commit("SET_IS_LOADING", true);
  },
  setAnswerData({ commit }, searchData) {
    mainApi
      .getAnswerData(searchData.surveyId, searchData.filterStr)
      .then((res) => {
        console.log(res);
        commit("SET_ANSWER_DATA_LIST", res.data.answerDataList);
        commit("SET_HIGHEST_AVERAGE_LIST", res.data.highestAverageList);
        commit(
          "SET_HIGHEST_STANDARD_DEVIATION_LIST",
          res.data.highestStandardDeviationList
        );
        commit("SET_LOWEST_AVERAGE_LIST", res.data.lowestAverageList);
        commit(
          "SET_LOWEST_STANDARD_DEVIATION_LIST",
          res.data.lowestStandardDeviationList
        );
        commit("SET_QUESTION_COUNT", res.data.questionCount);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  setRadarLabels({ commit }, radarLabels) {
    commit("SET_RADAR_LABELS", radarLabels);
  },
  setRadarDataSets({ commit }, radarDataSets) {
    commit("SET_RADAR_DATA_SETS", radarDataSets);
  },
  setBarLabels({ commit }, barLabels) {
    commit("SET_BAR_LABELS", barLabels);
  },
  setBarDataSets({ commit }, barDataSets) {
    commit("SET_BAR_DATA_SETS", barDataSets);
  },
  setComparisonDataSets({ commit }, comparisonDataSets) {
    commit("SET_COMPARISON_DATA_SETS", comparisonDataSets);
  },
  setComparisonLabels({ commit }, comparisonDataSets) {
    commit("SET_COMPARISON_LABELS", comparisonDataSets);
  },
  setIsLoading({ commit }, isLoading) {
    commit("SET_IS_LOADING", isLoading);
  },
};

const getters = {
  getAnswerDataList(state) {
    return state.answerDataList;
  },
  getRadarLabels(state) {
    return state.radarLabels;
  },
  getRadarDataSets(state) {
    return state.radarDataSets;
  },
  getComparisonDataSets(state) {
    return state.comparisonDataSets;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
