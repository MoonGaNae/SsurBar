import mainApi from '@/api/list.js';

const state = {
    surveyList : [],
    recentList : [],
    recentSurvey:{
      surveyId:"",
      creantionTime: "",
      title: "",
      endTime: "",
      teamName: ""
    }
  };
  
  const mutations = {
    SET_ONGOING_SURVEY_LIST: (state, payload) => {
      state.surveyList = payload;
      console.log(state.surveyList)
    },
    SET_RECENT_SURVEY_LIST: (state, payload) => {
      console.log(payload);
      state.recentList = payload;
    },
    SET_RECENT_TEMPLATE: (state, payload) => {
      console.log(payload);
      state.recentSurvey = payload;
    },
  };
  const actions = {
    getOngoingSurveyList({commit}) {
        mainApi.getOngoingSurveyList()
            .then((res) => {
                console.log(res)
                commit('SET_ONGOING_SURVEY_LIST', res.data.surveyList)
            })
            .catch((err) => {
                console.log(err);
            })
    },
    getRecentSurveyList({commit}) {
      mainApi.getRecentSurveyList()
          .then((res) => {
              console.log(res)
              commit('SET_RECENT_SURVEY_LIST', res.data.surveyList)
          })
          .catch((err) => {
              console.log(err);
          })
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
  