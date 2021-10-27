package com.ssurbar.survey.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.response.SurveyInfo;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.Team;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyForm;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;
import com.ssurbar.survey.db.repository.survey.SurveyRepository;
import com.ssurbar.survey.db.repository.survey.SurveyResponseLogRepository;
import com.ssurbar.survey.db.repository.survey.SurveyTargetRepository;
import com.ssurbar.survey.db.repository.survey.TeamRepository;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;
    
    @Autowired
    SurveyTargetRepository surveyTargetRepository;
    
    @Autowired
    SurveyResponseLogRepository surveyResponseLogRepository;
    
    @Autowired
    TeamRepository teamRepository;
    
    @Autowired
    RandomIdUtil randomIdUtil;

    /* 새로운 설문지 생성 */
    @Override
    public boolean createNewSurvey(SurveyCreatePostReq surveyCreatePostReq) {
    	SurveyForm surveyForm = SurveyForm.builder().surveyFormId(surveyCreatePostReq.getSurveyFormId()).build();
    	
    	List<Team> teamList = teamRepository.findAllById(surveyCreatePostReq.getTeamIdList());
    	
    	
    	List<SurveyTarget> surveyTargetList = surveyTargetRepository.findAll();
    	List<Survey> surveyList = surveyRepository.findAll();
    	/*------------------ survey target 데이터 생성 및 저장 시작  -----------------------*/
    	
    	for (Team team : teamList) {
    		String surveyTargetId = randomIdUtil.makeRandomId(13);
    		
    		boolean isSurveyTargetIdExist = false;
    		
    		while(true) {
    			for (SurveyTarget surveyTarget : surveyTargetList) {
    				if(surveyTarget.getSurveyTargetId().equals(surveyTargetId)) {
    					isSurveyTargetIdExist = true;
    					break;
    				} 
    			}
    			
    			if(!isSurveyTargetIdExist) {
    				break;
    			}
    			
    			surveyTargetId = randomIdUtil.makeRandomId(13);
    			isSurveyTargetIdExist = false;
    		}
    		
    		SurveyTarget savedSurverTarget = surveyTargetRepository.save(SurveyTarget.builder()
	    		.surveyTargetId(surveyTargetId)
	    		.team(team)
	    		.responseUrl("testResUrl")
	    		.resultUrl("testResultUrl")
	    		.build());
    		
			surveyTargetList.add(savedSurverTarget);
			
	    	/*------------------ survey 데이터 생성 및 저장 시작  -----------------------*/
	    	String surveyId = randomIdUtil.makeRandomId(13);
	    	
	    	boolean isSurveyIdExist = false;
	    	
	    	
	    	while(true) {
	    		for (Survey survey : surveyList) {
	    			if(survey.getSurveyId().equals(surveyId)) {
	    				isSurveyIdExist = true;
	    				break;
	    			} 
				}
	    		
	    		if(!isSurveyIdExist) {
	    			break;
	    		}
	    		
	    		surveyId = randomIdUtil.makeRandomId(13);
	    		isSurveyIdExist = false;
	    	}
	    	
	        Survey savedSurvey = surveyRepository.save(Survey.builder()
	        		.surveyId(surveyId)
	        		.surveyForm(surveyForm)
	        		.creationTime(surveyCreatePostReq.getCreationTime())
	        		.endTime(surveyCreatePostReq.getEndTime())
	        		.surveyTarget(savedSurverTarget)
	        		.build());
	        
	        surveyList.add(savedSurvey);
	        /*------------------ survey 데이터 생성 및 저장 끝  -----------------------*/
	        
		}
    	/*------------------ survey target 데이터 생성 및 저장 끝  -----------------------*/
    	
//    	/*------------------ survey 데이터 생성 및 저장 시작  -----------------------*/
//    	String surveyId = randomIdUtil.makeRandomId(13);
//    	
//    	boolean isSurveyIdExist = false;
//    	
//    	List<Survey> surveyList = surveyRepository.findAll();
//    	
//    	while(true) {
//    		for (Survey survey : surveyList) {
//    			if(survey.getSurveyId().equals(surveyId)) {
//    				isSurveyIdExist = true;
//    				break;
//    			} 
//			}
//    		
//    		if(!isSurveyIdExist) {
//    			break;
//    		}
//    		
//    		surveyId = randomIdUtil.makeRandomId(13);
//    		isSurveyIdExist = false;
//    	}
//    	
//        Survey survey = Survey.builder()
//        		.surveyId(surveyId)
//        		.surveyForm(surveyForm)
//        		.creationTime(surveyCreatePostReq.getCreationTime())
//        		.endTime(surveyCreatePostReq.getEndTime())
//        		.build();
//        
//        surveyRepository.save(survey);
//        /*------------------ survey 데이터 생성 및 저장 끝  -----------------------*/

        return true;
    }

	@Override
	public List<SurveyInfo> getAllSurveyList() {
		
		List<Survey> surveyList = surveyRepository.findAll();
		
		List<SurveyInfo> list = new ArrayList<>();
		
		for (Survey survey : surveyList) {
			list.add(SurveyInfo.builder()
					.creationTime(survey.getCreationTime())
					.endTime(survey.getEndTime())
					.surveyId(survey.getSurveyId())
					.title(survey.getSurveyForm().getTitle())
					.teamName(survey.getSurveyTarget().getTeam().getName())
					.build());
		}
		
		return list;
	}

	@Override
	public int getSurveyResponseCount(String surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElse(null);
		
		List<SurveyResponseLog> list = surveyResponseLogRepository.findAllBySurveyTarget(survey.getSurveyTarget());
		
		return list.size();
	}

	@Override
	public List<SurveyInfo> getMySurveyList(String accessToken) {
//		String myId = "";
//		
//		User user = User.builder().
//		
//		List<Survey> list = surveyRepository.findAllByUserId();
//		
		return null;
	}

}
