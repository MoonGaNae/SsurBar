package com.ssurbar.survey.api.service;

import java.util.List;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.db.entity.survey.Survey;

public interface SurveyService {
    // 새로운 설문지 생성
	Survey createNewSurvey(SurveyCreatePostReq surveyCreatePostReq);

	List<Survey> getAllSurveyList();

	List<Survey> getMySurveyList(String accessToken);

	int getSurveyResponseCount(String surveyId);
}
