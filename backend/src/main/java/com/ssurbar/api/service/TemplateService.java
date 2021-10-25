package com.ssurbar.api.service;

public interface TemplateService {
    // 설문 서식지 생성
    void createNewTemplate();
    // 설문에 대한 질문 생성
    void createNewQuestions();
    // 설문에 대한 필터 생성
    void createNewFilters();
}
