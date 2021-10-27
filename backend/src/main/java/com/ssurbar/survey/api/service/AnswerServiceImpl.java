package com.ssurbar.survey.api.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssurbar.survey.api.request.FilterDataReq;
import com.ssurbar.survey.api.request.SurveyAnswerListGetReq;
import com.ssurbar.survey.api.response.SurveyAnswer;
import com.ssurbar.survey.db.entity.answer.FilterData;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.repository.survey.QuestionAnswerRepository;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	QuestionAnswerRepository questionAnswerRepository;

	@Override
	public List<SurveyAnswer> getSurveyAnswerList(String surveyId, SurveyAnswerListGetReq surveyAnswerListGetReq) {
		Survey survey = Survey.builder().surveyId(surveyId).build();
		
		List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllBySurvey(survey);
		
		List<SurveyAnswer> surveyAnswerList = new ArrayList<>();
		
		List<FilterDataReq> filterDataList = surveyAnswerListGetReq.getFilterDataList();
		
		for (QuestionAnswer questionAnswer : questionAnswerList) {
			FilterData filterData = questionAnswer.getFilterData();
			String filterRes = filterData.getResponse();
			
			JSONParser jsonParse = new JSONParser();
			
			try {
				JSONObject jsonObj =  (JSONObject) jsonParse.parse(filterRes);
				boolean isCorrect = true;
				
				for (FilterDataReq filterDataReq : filterDataList) {
					String filterKind = filterDataReq.getFilterKind();
					String filterValue = filterDataReq.getFilterValue();
					
					if(!filterValue.equals(jsonObj.get(filterKind))) {
						isCorrect = false;
						break;
					}
				}
				
				if(isCorrect)	surveyAnswerList.add(SurveyAnswer.builder().questionId(questionAnswer.getQuestion().getQuestionId()).response(questionAnswer.getResponse()).build());
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return surveyAnswerList;
	}

}
