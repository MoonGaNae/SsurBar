package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.api.request.TemplatePostReq;

import java.util.List;

public interface TemplateService {
    // 설문 서식지 생성
    String createNewTemplate(TemplatePostReq templatePostReq);
    // 설문에 대한 질문 생성
    List<String> createNewQuestions(String templateId, TemplateQuestionListPostReq templateQuestionListPostReq);
    // 설문에 대한 필터 생성
    void createNewFilters();


}
