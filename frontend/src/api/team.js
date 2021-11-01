import _axios from '@/utils/axios';

export default {
    // 팀 리스트 조회
    getTeams() {
        return _axios({
            url: `/team`,
            method: 'get'
        })
    },
}