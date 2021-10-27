package com.ssurbar.survey.api.service;

import com.ssurbar.survey.db.repository.survey.TemplateRepository;
import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.db.entity.survey.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface TemplateService {
    // 설문 서식지 생성
    String createNewTemplate(TemplatePostReq templatePostReq);
    // 설문에 대한 질문 생성
    void createNewQuestions();
    // 설문에 대한 필터 생성
    void createNewFilters();


}
