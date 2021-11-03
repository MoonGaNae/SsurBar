import axios from "axios";

// axios 객체 생성
export default axios.create({
  baseURL: "http://k5f001.p.ssafy.io:8080/api/v1",
  // baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-type": "application/json",
  },
  //withCredentials: true,
});
