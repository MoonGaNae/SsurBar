package com.ssurbar.survey.api.service;

import java.util.List;

import com.ssurbar.survey.api.request.SurveyAnswerListGetReq;
import com.ssurbar.survey.api.response.SurveyAnswer;

public interface AnswerService {

	List<SurveyAnswer> getSurveyAnswerList(String surveyId, SurveyAnswerListGetReq surveyAnswerListGetReq);

}