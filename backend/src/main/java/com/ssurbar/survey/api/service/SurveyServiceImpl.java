package com.ssurbar.survey.api.service;

import com.google.gson.Gson;
import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.request.SurveyDecodeLinkGetReq;
import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.response.*;
import com.ssurbar.survey.common.util.LinkUtil;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.Team;
import com.ssurbar.survey.db.entity.survey.FilterQuestion;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.repository.survey.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    SurveyRepository surveyRepository;
    
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

	@Autowired
	TemplateRepository templateRepository;

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
				.creationTime(new Date())
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

//	@Override
//	public SurveyDetailInfo getSurveyInfo(String surveyId) {
//    	Optional<Survey> survey = surveyRepository.findBySurveyId(surveyId);
//    	if(survey.isPresent()){
//			SurveyDetailInfo info = SurveyDetailInfo.builder()
//					.title(survey.get().getTemplate().getTitle())
//					.teamName(survey.get().getTeam().getName())
//					.creationTime(survey.get().getCreationTime())
//					.endTime(survey.get().getEndTime())
//					.descrption(survey.get().getTemplate().getDescription())
//					.build();
//
//			return info;
//		}
//		return null;
//	}

	@Override
	public List<RecentSurveyInfo> getRecentSurveyList() {
    	// 로그인이 가능해지면 사용자 아이디별로 가져오는 로직으로 변경
		List<Survey> surveyList = surveyRepository.findAll(Sort.by(Sort.Direction.DESC, "creationTime"));
		List<RecentSurveyInfo> list = new ArrayList<>();

		for (Survey survey : surveyList) {
			list.add(RecentSurveyInfo.builder()
					.creationTime(survey.getCreationTime())
					.endTime(survey.getEndTime())
					.surveyId(survey.getSurveyId())
					.title(survey.getTemplate().getTitle())
					.teamName(survey.getTeam().getName())
					.templateId(survey.getTemplate().getTemplateId())
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

		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

		String dateStr = formatter.format(new Date());

		Date now = null;

		try{
			now = formatter.parse(dateStr);
		}
		catch(ParseException e) {
			e.getStackTrace();
		}

		SurveyDetailRes surveyDetailRes = SurveyDetailRes.builder()
				.surveyId(survey.getSurveyId())
				.templateId(survey.getTemplate().getTemplateId())
				.creationTime(survey.getCreationTime())
				.endTime(survey.getEndTime())
				.responseUrl(survey.getResponseUrl())
				.resultUrl(survey.getResultUrl())
				.teamId(survey.getTeam().getTeamId())
				.teamName(survey.getTeam().getName())
				.title(survey.getTemplate().getTitle())
				.description(survey.getTemplate().getDescription())
				.build();

		return surveyDetailRes;
	}

	@Override
	public RecentSurveyDetailRes getRecentSurveyDetailInfo(String surveyId) {
		Optional<Survey> survey = surveyRepository.findById(surveyId);
		Optional<Template> template = templateRepository.getTemplateByTemplateId(survey.get().getTemplate().getTemplateId());

		if(survey.isPresent()){
			RecentSurveyDetailRes surveyDetailRes = RecentSurveyDetailRes.builder()
					.templateId(template.get().getTemplateId())
					.teamName(survey.get().getTeam().getName())
					.title(survey.get().getTemplate().getTitle())
					.teamId(survey.get().getTeam().getTeamId())
					.description(template.get().getDescription())
					.build();
			return surveyDetailRes;
		}

		return null;
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

	@Override
	public SameTemplateSurveyRes getSameTemplateSurvey(String surveyId) {

		Survey survey = surveyRepository.findById(surveyId).orElse(null);

		List<Survey> surveyList = surveyRepository.findAllByTemplateAndSurveyIdNot(survey.getTemplate(), surveyId);

		List<SurveyInfo> surveyInfoList = new ArrayList<>();

		for(Survey s : surveyList){
			SurveyInfo surveyInfo = SurveyInfo.builder()
					.surveyId(s.getSurveyId())
					.title(s.getTemplate().getTitle())
					.teamName(s.getTeam().getName())
					.creationTime(s.getCreationTime())
					.endTime(s.getEndTime())
					.build();

			surveyInfoList.add(surveyInfo);
		}

		if(surveyInfoList.size() == 0){
			return null;
		}

		SameTemplateSurveyRes sameTemplateSurveyRes = SameTemplateSurveyRes.builder()
				.surveyInfoList(surveyInfoList).build();

		return sameTemplateSurveyRes;
	}
}
