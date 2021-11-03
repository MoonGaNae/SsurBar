package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.FeedBackPatchReq;
import com.ssurbar.survey.api.request.FeedBackPostReq;
import com.ssurbar.survey.db.entity.survey.IntegratedFeedback;

public interface FeedbackService {
    /* 통합 피드백 저장하기 */
    IntegratedFeedback addTotalFeedback(FeedBackPostReq feedBackPostReq);

    /* 통합 피드백 편집하기 */
    IntegratedFeedback editTotalFeedback(String surveyId, FeedBackPatchReq feedBackPatchReq);

    /* 통합 피드백 불러오기 */
    IntegratedFeedback getTotalFeedback(String surveyId);

    /* 통합 피드백 삭제하기 */
    void deleteTotalFeedback(String surveyId);
}
