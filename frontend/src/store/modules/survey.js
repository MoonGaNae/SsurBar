const state = {
  surveyId: null,
};

const mutations = {
  SET_SURVEY_ID: (state, surveyId) => {
    state.surveyId = surveyId;
  },
};

const actions = {
  setSurveyId({ commit }, surveyId) {
    commit("SET_SURVEY_ID", surveyId);
  },
};

const getters = {
  getSurveyId(state) {
    return state.surveyId;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
