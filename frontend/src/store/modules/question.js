const state = {
  questionList: null,
};

const mutations = {
  SET_QUESTION_LIST: (state, questionList) => {
    state.questionList = questionList;
  },
};

const actions = {
  setQuestionList({ commit }, questionList) {
    commit("SET_QUESTION_LIST", questionList);
  },
};

const getters = {
  getQuestionList(state) {
    return state.questionList;
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
