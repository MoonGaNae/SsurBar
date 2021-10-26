package com.ssurbar.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssurbar.api.request.SurveyCreatePostReq;
import com.ssurbar.common.util.RandomIdUtil;
import com.ssurbar.db.entity.survey.Survey;
import com.ssurbar.db.entity.survey.SurveyForm;
import com.ssurbar.db.repository.survey.SurveyRepository;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;
    
    @Autowired
    RandomIdUtil randomIdUtil;

    /* 새로운 설문지 생성 */
    @Override
    public Survey createNewSurvey(SurveyCreatePostReq surveyCreatePostReq) {
    	SurveyForm surveyForm = SurveyForm.builder().surveyFormId(surveyCreatePostReq.getSurveyFormId()).build();
    	
    	String surveyId = randomIdUtil.makeRandomId(13);
    	
    	boolean isExist = false;
    	
    	List<Survey> surveyList = surveyRepository.findAllBySurveyFormId(surveyForm);
    	
    	while(true) {
    		for (Survey survey : surveyList) {
    			if(survey.getSurveyId().equals(surveyId)) {
    				isExist = true;
    				break;
    			} 
			}
    		
    		if(!isExist) {
    			break;
    		}
    		
    		surveyId = randomIdUtil.makeRandomId(13);
    		isExist = false;
    	}
    	
        Survey survey = Survey.builder()
        		.surveyForm(surveyForm)
        		.creationTime(surveyCreatePostReq.getCreationTime())
        		.endTime(surveyCreatePostReq.getEndTime())
        		.build();
        
        surveyRepository.save(survey);

        return survey;
    }

	@Override
	public List<Survey> getAllSurveyList() {
		
		List<Survey> list = surveyRepository.findAll();
		
		return list;
	}

}
