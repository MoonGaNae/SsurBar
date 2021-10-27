package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;
import com.ssurbar.survey.db.repository.survey.SurveyRepository;
import com.ssurbar.survey.db.repository.survey.SurveyResponseLogRepository;
import com.ssurbar.survey.db.repository.survey.SurveyTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;
    
    @Autowired
    SurveyTargetRepository surveyTargetRepository;
    
    @Autowired
    SurveyResponseLogRepository surveyResponseLogRepository;
    
    @Autowired
    RandomIdUtil randomIdUtil;

    /* 새로운 설문지 생성 */
    @Override
    public Survey createNewSurvey(SurveyCreatePostReq surveyCreatePostReq) {
    	Template template = Template.builder().templateId(surveyCreatePostReq.getTemplateId()).build();
    	
    	String surveyId = randomIdUtil.makeRandomId(13);
    	
    	boolean isExist = false;
    	
    	List<Survey> surveyList = surveyRepository.findAllByTemplate(template);
    	
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
        		.template(template)
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

	@Override
	public int getSurveyResponseCount(String surveyId) {
		Survey survey = Survey.builder().surveyId(surveyId).build();
		
		SurveyTarget surveyTarget = surveyTargetRepository.findBySurvey(survey);
		
		List<SurveyResponseLog> list = surveyResponseLogRepository.findAllBySurveyTarget(surveyTarget);
		
		return list.size();
	}

	@Override
	public List<Survey> getMySurveyList(String accessToken) {
//		String myId = "";
//		
//		User user = User.builder().
//		
//		List<Survey> list = surveyRepository.findAllByUserId();
//		
		return null;
	}

}
