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
};
