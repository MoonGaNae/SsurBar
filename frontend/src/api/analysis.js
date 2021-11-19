import _axios from "@/utils/axios";

export default {
  getAnswerData(surveyId, filterStr) {
    return _axios({
      url: `/survey/${surveyId}/answer`,
      method: "get",
      params: {
        filterDataStr: encodeURI(filterStr),
      },
    });
  },
  // 설문응답 raw데이터가져오기
  getAnswerRawData(surveyId) {
    return _axios({
      url: `/survey/${surveyId}/answer-raw`,
      method: `get`
    });
  }
};
