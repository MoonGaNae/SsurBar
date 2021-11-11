import router from '@/router'
import userApi from '@/api/user';

const state = {
  token : null,
  isLogin: null,
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_IS_LOGIN: (state, isLogin) => {
    state.isLogin = isLogin;
  }
};

const actions = {
    moveLogin(){
      router.push('/login');
    },
    async onLogin( {commit}, userData) {
      await userApi.login(userData)
        .then( () => {
          commit('SET_IS_LOGIN', true);

          router.push("/");
        })
        .catch( (err) => {
          console.log(err);
        })
    },
    onLogout( {commit, dispatch}) {
        commit("SET_TOKEN", null);
        commit("SET_IS_LOGIN", null);
        dispatch('moveLogin');
    }
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
