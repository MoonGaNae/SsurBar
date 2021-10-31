package com.ssurbar.survey.api.service;

import java.util.List;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.request.SurveyDecodeLinkGetReq;
import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.response.FilterQuestionDetail;
import com.ssurbar.survey.api.response.SurveyDecodeLinkGetRes;
import com.ssurbar.survey.api.response.SurveyDetailRes;
import com.ssurbar.survey.api.response.SurveyInfo;

public interface SurveyService {
    // 새로운 설문지 생성
	boolean createNewSurvey(SurveyCreatePostReq surveyCreatePostReq);

	List<SurveyInfo> getAllSurveyList();

	List<SurveyInfo> getMySurveyList(String accessToken);

	int getSurveyResponseCount(String surveyId);

	SurveyDetailRes getSurveyDetailInfo(String surveyId);

	// 설문에 대한 필터 생성
	List<String> createNewFilters(String surveyId, SurveyFilterListPostReq surveyFilterListPostReq);

	// 설문 필터 목록 가져오기
	List<FilterQuestionDetail> getFilters(String surveyId);

    SurveyDecodeLinkGetRes decodeLink(SurveyDecodeLinkGetReq surveyDecodeLinkGetReq);
}
