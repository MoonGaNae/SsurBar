import _axios from '@/utils/axios';

export default {
    // 기본서식 조회
    getDefaultTemplates() {
        return _axios({
            url: `/template/default`,
            method: 'get'
        })
    },
}