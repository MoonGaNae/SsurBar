package com.ssurbar.survey.api.service;

import com.google.gson.Gson;
import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.request.SurveyDecodeLinkGetReq;
import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.response.FilterQuestionDetail;
import com.ssurbar.survey.api.response.SurveyDecodeLinkGetRes;
import com.ssurbar.survey.api.response.SurveyDetailRes;
import com.ssurbar.survey.api.response.SurveyInfo;
import com.ssurbar.survey.common.util.LinkUtil;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.Team;
import com.ssurbar.survey.db.entity.survey.FilterQuestion;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.repository.survey.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	FilterQuestionRepository filterQuestionRepository;

    /* 새로운 설문지 생성 */
    @Override
    public String createNewSurvey(SurveyCreatePostReq surveyCreatePostReq) {
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

		return surveyId;

	}

	@Override
	public List<SurveyInfo> getAllSurveyList() {
		
		List<Survey> surveyList = surveyRepository.findAll();
		
		List<SurveyInfo> list = new ArrayList<>();
		
		for (Survey survey : surveyList) {
			String surveyId = survey.getSurveyId();
			int cnt = getSurveyResponseCount(surveyId);
			list.add(SurveyInfo.builder()
					.creationTime(survey.getCreationTime())
					.endTime(survey.getEndTime())
					.surveyId(survey.getSurveyId())
					.title(survey.getTemplate().getTitle())
					.teamName(survey.getTeam().getName())
					.cnt(cnt)
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

	@Override
	public List<String> createNewFilters(String surveyId, SurveyFilterListPostReq surveyFilterListPostReq) {
		Survey survey = surveyRepository.getById(surveyId);

		Gson gson = new Gson();

		// 필터문항 추출및 Entity로 변환
		List<FilterQuestion> filterSaveList = new ArrayList<>();
		for(String filterJson : surveyFilterListPostReq.getFilterQuestionList()){
			SurveyFilterListPostReq.FilterDto filterDto = gson.fromJson(filterJson, SurveyFilterListPostReq.FilterDto.class);

			String filterId = randomIdUtil.makeRandomId(13);
			filterSaveList.add(FilterQuestion.builder()
					.filterQuestionId(filterId)
					.questionNum(filterDto.getNumber())
					.title(filterDto.getTitle())
					.content(filterDto.getContent())
					.survey(survey)
					.build());
		}

		// 필터문항 저장
		filterSaveList = filterQuestionRepository.saveAll(filterSaveList);

		// 필터문항 키들 반환
		List<String> idList = new ArrayList<>();
		for(FilterQuestion filter : filterSaveList){
			idList.add(filter.getFilterQuestionId());
		}

		return idList;
	}

	@Override
	public List<FilterQuestionDetail> getFilters(String surveyId) {

		Survey survey = surveyRepository.findById(surveyId).orElse(null);

		if(survey == null) return null;

		List<FilterQuestion> filterQuestions = survey.getFilterQuestions();

		return filterQuestions.stream().map(FilterQuestionDetail::of).collect(Collectors.toList());
	}

	@Override
	public SurveyDecodeLinkGetRes decodeLink(SurveyDecodeLinkGetReq surveyDecodeLinkGetReq) {

		String linkCode= surveyDecodeLinkGetReq.getLinkCode();
		String type = surveyDecodeLinkGetReq.getType();

		Survey survey = null;

		// type 별로 url 조회 다르게 answer or result
		if(type.equals("answer")){
			survey = surveyRepository.findSurveyByResponseUrl(linkCode).orElse(null);
		}else if(type.equals("result")){
			survey = surveyRepository.findSurveyByResultUrl(linkCode).orElse(null);
		}

		if(survey == null) return null;

		return SurveyDecodeLinkGetRes.builder()
				.surveyId(survey.getSurveyId())
				.templateId(survey.getTemplate().getTemplateId())
				.build();
	}
}
