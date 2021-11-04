import mainApi from '@/api/list.js';

const state = {
    surveyList : [],
  };
  
  const mutations = {
    SET_ONGOING_SURVEY_LIST: (state, payload) => {
      state.surveyList = payload;
      console.log(state.surveyList)
    },
    // SET_SURVEY_COUNT: (state, payload) => {
    //   console.log(payload);
    //   state.surveyCountList = payload;
    // },
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
    // async getSurveyCount({ commit }, surveyId) {
    //   await mainApi.getSurveyCount(surveyId)
    //     .then((res) => {
    //       console.log(res)
    //       commit('SET_SURVEY_COUNT', res.data)
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //   })
    // },
  };
  
  const getters = {
    getOngoingSurveyListState(state) {
      return state.surveyList;
    },
  };
  
  export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
  };
  