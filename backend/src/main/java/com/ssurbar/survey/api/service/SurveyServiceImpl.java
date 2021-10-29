package com.ssurbar.survey.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.response.SurveyDetailRes;
import com.ssurbar.survey.api.response.SurveyInfo;
import com.ssurbar.survey.common.util.LinkUtil;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.Team;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.Template;
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
    
    @Autowired
    LinkUtil linkUtil;

    /* 새로운 설문지 생성 */
    @Override
    public boolean createNewSurvey(SurveyCreatePostReq surveyCreatePostReq) {
    	Template template = Template.builder().templateId(surveyCreatePostReq.getTemplateId()).build();
    	
    	Team team = teamRepository.findById(surveyCreatePostReq.getTeamId()).orElse(null);
    	
    	
    	List<Survey> surveyList = surveyRepository.findAllByTemplate(template);

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

		String responseUrl = linkUtil.makeUrl(surveyId, "response");
		String resultUrl = linkUtil.makeUrl(surveyId, "result");

		Survey savedSurvey = surveyRepository.save(Survey.builder()
				.surveyId(surveyId)
				.template(template)
				.creationTime(surveyCreatePostReq.getCreationTime())
				.endTime(surveyCreatePostReq.getEndTime())
				.team(team)
				.responseUrl(responseUrl)
				.resultUrl(resultUrl)
				.build());

		surveyList.add(savedSurvey);
		/*------------------ survey 데이터 생성 및 저장 끝  -----------------------*/

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
					.title(survey.getTemplate().getTitle())
					.teamName(survey.getTeam().getName())
					.build());
		}
		
		return list;
	}

	@Override
	public int getSurveyResponseCount(String surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElse(null);
		
		List<SurveyResponseLog> list = surveyResponseLogRepository.findAllBySurvey(survey);
		
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

	@Override
	public SurveyDetailRes getSurveyDetailInfo(String surveyId) {
		Survey survey = surveyRepository.findById(surveyId).orElse(null);
		
		if(survey == null)	return null;
		
		SurveyDetailRes surveyDetailRes = SurveyDetailRes.builder()
				.surveyId(survey.getSurveyId())
				.templateId(survey.getTemplate().getTemplateId())
				.creationTime(survey.getCreationTime())
				.endTime(survey.getEndTime())
				.responseUrl(survey.getResponseUrl())
				.resultUrl(survey.getResultUrl())
				.teamId(survey.getTeam().getTeamId())
				.teamName(survey.getTeam().getName())
				.build();

		return surveyDetailRes;
	}

}
