package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.ResponsePostReq;
import com.ssurbar.survey.api.response.SurveyAnswer;

import java.util.List;

public interface ResponseService {
    /* 설문지에 대한 응답자의 답변 저장 */
    List<SurveyAnswer> saveAnswer(ResponsePostReq responsePostReq);

}
