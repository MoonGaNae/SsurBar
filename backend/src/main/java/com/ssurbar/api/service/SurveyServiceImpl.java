package com.ssurbar.api.service;

import com.ssurbar.db.entity.survey.Survey;
import com.ssurbar.db.repository.survey.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    /* 새로운 설문지 생성 */
    @Override
    public Survey createNewSurvey() {
        Survey survey = Survey.builder().build();
        surveyRepository.save(survey);

        return survey;
    }

}
