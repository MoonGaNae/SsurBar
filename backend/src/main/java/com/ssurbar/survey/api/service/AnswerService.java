package com.ssurbar.survey.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssurbar.survey.api.response.SurveyAnswerRawGetRes;
import com.ssurbar.survey.api.response.SurveyResultRes;

import java.io.UnsupportedEncodingException;

public interface AnswerService {

	SurveyResultRes getSurveyAnswerList(String surveyId, String filterDataStr) throws JsonProcessingException, UnsupportedEncodingException;

    SurveyAnswerRawGetRes getSurveyAnswerRaw(String surveyId);
}
