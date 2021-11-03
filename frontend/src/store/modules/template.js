const state = {
  templateId: null,
  createTime: null,
  endTime: null,
  teamId: null,
  description: null,
  title: null,
};

const mutations = {
  SET_TEMPLATE_ID: (state, templateId) => {
    state.templateId = templateId;
  },
  SET_CREATE_TIME: (state, createTime) => {
    state.createTime = createTime;
  },
  SET_END_TIME: (state, endTime) => {
    state.endTime = endTime;
  },
  SET_TEAM_ID: (state, teamId) => {
    state.teamId = teamId;
  },
  SET_DESCRIPTION: (state, description) => {
    state.description = description;
  },
  SET_TITLE: (state, title) => {
    state.title = title;
  },
};

const actions = {
  setTemplateId({ commit }, templateId) {
    commit("SET_TEMPLATE_ID", templateId);
  },
  setCreateTime({ commit }, createTime) {
    commit("SET_CREATE_TIME", createTime);
  },
  setEndTime({ commit }, endTime) {
    commit("SET_END_TIME", endTime);
  },
  setTeamId({ commit }, teamId) {
    commit("SET_TEAM_ID", teamId);
  },
  setDescription({ commit }, description) {
    commit("SET_DESCRIPTION", description);
  },
  setTitle({ commit }, title) {
    commit("SET_TITLE", title);
  },
};

const getters = {
  getTemplateId(state) {
    return state.templateId;
  },
  getCreateTime(state) {
    return state.createTime;
  },
  getEndTime(state) {
    return state.endTime;
  },
  getTeamId(state) {
    return state.teamId;
  },
  getDescription(state) {
    return state.description;
  },
  getTitle(state) {
    return state.title;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
