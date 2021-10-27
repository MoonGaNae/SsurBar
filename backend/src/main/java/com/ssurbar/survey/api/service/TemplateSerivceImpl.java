package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.repository.survey.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TemplateServiceImpl implements TemplateService{

    @Autowired
    TemplateRepository templateRepository;

    /* 새로운 설문서식 생성 */
    @Override
    public String createNewTemplate(TemplatePostReq templatePostReq) {

        String title = templatePostReq.getTitle();
        String description = templatePostReq.getDescription();
        Template template = Template.builder()
                .title(title)
                .description(description)
                .build();

        return templateRepository.save(template).getTemplateId();
    }

    @Override
    public void createNewQuestions() {

    }

    @Override
    public void createNewFilters() {

    }
}