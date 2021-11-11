import _axios from '@/utils/axios';

export default {
    // 기본서식 조회
    login(userData) {
        return _axios({
            url: `/users/login`,
            method: 'post',
            data: userData
        })
    },


}