package com.ssurbar.survey.api.service;

import java.util.List;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.response.SurveyInfo;
import com.ssurbar.survey.db.entity.survey.Survey;

public interface SurveyService {
    // 새로운 설문지 생성
	boolean createNewSurvey(SurveyCreatePostReq surveyCreatePostReq);

	List<SurveyInfo> getAllSurveyList();

	List<SurveyInfo> getMySurveyList(String accessToken);

	int getSurveyResponseCount(String surveyId);
}
