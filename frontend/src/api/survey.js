import axios from '@/utils/axios';

export default {
    // 설문지 정보 불러오기
    getSurveyInfo(surveyId) {
        return axios({
            url: `/survey/recent/${surveyId}`,
            method: 'get'
        })
    },
}