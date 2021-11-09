import surveyApi from '@/api/survey.js';

const state = {
  surveyId: null,
  recentSurvey:{
    templateId: "",
    title: "",
    teamName: "",
    teamId: "",
    description: ""
  },

  surveyCreateType: {
    NEW : 0,
    DEFAULT : 1,
    RECENT : 2,
  },
  curCreateType: null,
};

const mutations = {
  SET_SURVEY_ID: (state, surveyId) => {
    state.surveyId = surveyId;
  },
  SET_RECENT_SURVEY_INFO:(state, payload) => {
    console.log(payload)
    state.recentSurvey.templateId = payload.templateId;
    state.recentSurvey.title = payload.title;
    state.recentSurvey.teamName = payload.teamName;
    state.recentSurvey.teamId = payload.teamId;
    state.recentSurvey.description = payload.description;
    console.log(state.recentSurvey)
  },

  SET_CUR_CREATE_TYPE: (state, type) => {
    state.curCreateType = type;
  }
};

const actions = {
  setSurveyId({ commit }, surveyId) {
    commit("SET_SURVEY_ID", surveyId);
  },
  async getRecentSurveyInfo({commit},surveyId) {
    await surveyApi.getSurveyInfo(surveyId)
        .then((res) => {
            console.log(res)
            commit('SET_RECENT_SURVEY_INFO', res.data)
        })
        .catch((err) => {
            console.log(err);
        })
  },

  setCreateType({ commit }, createType) {
    commit('SET_CUR_CREATE_TYPE', createType);
  }
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
