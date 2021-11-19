import _axios from '@/utils/axios';

const resource ='template';
export default {
    // 기본서식 조회
    getDefaultTemplates() {
        return _axios({
            url: `/${resource}/default`,
            method: 'get'
        })
    },

    // 서식상세정보 조회
    getTemplateDetailInfo(templateId){
        return _axios({
            url: `/${resource}/${templateId}/info`,
            method: 'get'
        });
    },
    // 서식 질문정보 조회
    getTemplateQuestionList(templateId){
        return _axios({
            url:  `/${resource}/${templateId}/questions`,
            method: 'get'
        });
    }


}