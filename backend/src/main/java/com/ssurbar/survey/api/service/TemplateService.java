package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.api.response.QuestionDetail;
import com.ssurbar.survey.api.response.TemplateInfo;
import com.ssurbar.survey.common.model.common.QuestionCreateResult;

import java.util.List;

public interface TemplateService {
    // 설문 서식지 생성
    String createNewTemplate(TemplatePostReq templatePostReq);
    // 설문에 대한 질문 생성
    QuestionCreateResult createNewQuestions(String templateId, TemplateQuestionListPostReq templateQuestionListPostReq);
    // 설문 서식에 연결된 문항리스트 가져오기
    List<QuestionDetail> getQuestions(String templateId);
    // 기본서식 목록 가져오기
    List<TemplateInfo> getDefaultTemplate();
    // 특정 서식에 대한 정보가져오기
    TemplateInfo getTemplate(String templateId);

}
