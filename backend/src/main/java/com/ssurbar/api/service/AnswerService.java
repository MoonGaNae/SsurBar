package com.ssurbar.api.service;

import java.util.List;

import com.ssurbar.api.request.SurveyAnswerListGetReq;
import com.ssurbar.api.response.SurveyAnswer;

public interface AnswerService {

	List<SurveyAnswer> getSurveyAnswerList(String surveyId, SurveyAnswerListGetReq surveyAnswerListGetReq);

}
