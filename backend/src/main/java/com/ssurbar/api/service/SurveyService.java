package com.ssurbar.api.service;

import java.util.List;

import com.ssurbar.api.request.SurveyCountGetReq;
import com.ssurbar.api.request.SurveyCreatePostReq;
import com.ssurbar.db.entity.survey.Survey;

public interface SurveyService {
    // 새로운 설문지 생성
	Survey createNewSurvey(SurveyCreatePostReq surveyCreatePostReq);

	List<Survey> getAllSurveyList();

	List<Survey> getMySurveyList(String accessToken);

	int getSurveyResponseCount(String surveyId);
}
