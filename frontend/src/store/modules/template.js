import templateApi from "@/api/template.js";
const state = {
  templateId: null,
  createTime: null,
  endTime: null,
  teamId: null,
  description: null,
  title: null,

  defaultTemplateList: null,
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
  SET_DEFAULT_TEMPLATE_LIST: (state, list) => {
    state.defaultTemplateList = list;
  },
};

const actions = {
  initTemplateData({ commit }) {
    commit("SET_TEMPLATE_ID", null);
    commit("SET_CREATE_TIME", null);
    commit("SET_END_TIME", null);
    commit("SET_TEAM_ID", null);
    commit("SET_DESCRIPTION", null);
    commit("SET_TITLE", null);
  },
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

  async getDefaultTemplates({ commit }) {
    await templateApi
      .getDefaultTemplates()
      .then((res) => {
        commit("SET_DEFAULT_TEMPLATE_LIST", res.data.templateList);
      })
      .catch((err) => {
        console.log(err);
      });
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
    console.log(state);
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
