import router from '@/router'
import userApi from '@/api/user';

const state = {
  token : null,
  isLogin: null,
  userInfo : {
    userId : null,
    userType : null,
  }
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_IS_LOGIN: (state, isLogin) => {
    state.isLogin = isLogin;
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
  },
  SET_LOGOUT:(state) =>{
    state.isLogin = null;
    state.userInfo.userId=null;
    state.userInfo.userType=null;
  }
  
};

const actions = {
    moveLogin(){
      router.push('/login');
    },
    async onLogin( {commit}, userData) {
      await userApi.login(userData)
        .then( (res) => {
          commit('SET_IS_LOGIN', true);
          commit('SET_USER_INFO', res.data);

          router.push("/");
        })
        .catch( (err) => {
          console.log(err);
        })
    },
    onLogout( {commit, dispatch}) {
        commit("SET_TOKEN", null);
        commit("SET_LOGOUT");
        dispatch('moveLogin');
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
