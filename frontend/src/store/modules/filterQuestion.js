const state = {
  filterQuestionList: null,
};

const mutations = {
  SET_FILTER_QUESTION_LIST: (state, filterQuestionList) => {
    state.filterQuestionList = filterQuestionList;
  },
};

const actions = {
  setFilterQuestionList({ commit }, filterQuestionList) {
    commit("SET_FILTER_QUESTION_LIST", filterQuestionList);
  },
};

const getters = {
  getFilterQuestionList(state) {
    return state.filterQuestionList;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
