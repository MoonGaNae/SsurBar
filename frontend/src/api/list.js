import axios from '@/utils/axios';

export default {
    // 진행중인 설문 조회
    getOngoingSurveyList() {
        return axios({
            url: `/survey`,
            method: 'get'
        })
    },
    // 설문 응답수 조회
    getSurveyCount(surveyId) {
        return axios({
            url: `/survey/${surveyId}/response/cnt`,
            method: 'get'
        })
    },
}