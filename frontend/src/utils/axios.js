import axios from "axios";
import store from "@/store/index.js";

// axios 객체 생성
const _axios = axios.create({
  baseURL: "http://k5f001.p.ssafy.io:8080/api/v1",
  // baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-type": "application/json",
  },
  withCredentials: true,
});

_axios.interceptors.request.use(
  (config) => {
    if(store.state.user.token){
      config.headers['authorization'] = store.state.user.token;
    }
    return config;
  },
  (err) => {
    return Promise.reject(err);
  }
)

_axios.interceptors.response.use(
  (response) => {
    if(response.headers.authorization){
      store.commit('user/SET_TOKEN', response.headers.authorization)
    }
    return Promise.resolve(response)
  },
  (err) => {
    if(err.response.status === 401){
      // 로그인페이지 보내기
    }

    return Promise.reject(err);
  }
)

export default _axios;
