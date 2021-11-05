import axios from '@/utils/axios';

export default {
    // 진행중인 설문 조회
    getOngoingSurveyList() {
        return axios({
            url: `/survey`,
            method: 'get'
        })
    },
    // 진행중인 설문 최신순으로 조회 
    getRecentSurveyList(){
        return axios({
            url: `/survey/recent`,
            method: 'get'
        })
    }
}