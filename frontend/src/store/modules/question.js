const state = {
  questionList: null,
  categoryList: null,
};

const mutations = {
  SET_QUESTION_LIST: (state, questionList) => {
    state.questionList = questionList;
  },
  SET_CATEGORY_LIST: (state, categoryList) => {
    state.categoryList = categoryList;
  },
};

const actions = {
  setQuestionList({ commit }, questionList) {
    commit("SET_QUESTION_LIST", questionList);
  },
  setCategoryList({ commit }, categoryList) {
    commit("SET_CATEGORY_LIST", categoryList);
  },
};

const getters = {
  getQuestionList(state) {
    return state.questionList;
  },
  getCategoryList(state) {
    return state.categoryList;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
