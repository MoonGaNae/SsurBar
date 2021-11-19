import _axios from "@/utils/axios";

export default {
  // 기본서식 조회
  login(userData) {
    return _axios({
      url: `/users/login`,
      method: "post",
      data: userData,
    });
  },

  getUncertificateUsers() {
    return _axios({
      url: `/users/uncertified`,
      method: `get`,
    });
  },

  signUp(userData) {
    return _axios({
      url: `/users/join`,
      method: `post`,
      data: userData,
    });
  },

  certificateUser(userId) {
    return _axios({
      url: `/users/certification`,
      method: `put`,
      data: {
        userId: userId,
        userType: "CERTIFIED",
      },
    });
  },
};
