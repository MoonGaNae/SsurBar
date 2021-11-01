import teamApi from '@/api/team.js';

const state = {
    teamList : null,
  };
  
  const mutations = {
    SET_TEAM_LIST: (state, payload) => {
      state.teamList = payload;
    },
  };
  
  const actions = {
    async getTeams({commit}) {
        await teamApi.getTeams()
            .then((res) => {
                commit('SET_TEAM_LIST', res.data.teamList)
            })
            .catch((err) => {
                console.log(err);
            })
    },
  };
  
  const getters = {
    
  };
  
  export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions,
  };
  