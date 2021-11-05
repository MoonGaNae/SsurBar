package com.ssurbar.survey.api.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssurbar.survey.api.request.SurveyAnswerListGetReq;
import com.ssurbar.survey.api.response.AnswerData;
import com.ssurbar.survey.api.response.SurveyAnswer;

public interface AnswerService {

	List<AnswerData> getSurveyAnswerList(String surveyId, String filterDataStr) throws JsonProcessingException, UnsupportedEncodingException;

}
