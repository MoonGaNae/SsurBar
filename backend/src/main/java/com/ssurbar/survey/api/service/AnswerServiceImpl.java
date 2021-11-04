package com.ssurbar.survey.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssurbar.survey.api.response.AnswerData;
import com.ssurbar.survey.api.response.QuestionData;
import com.ssurbar.survey.db.entity.survey.Question;
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
import com.ssurbar.survey.db.repository.answer.QuestionAnswerRepository;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	QuestionAnswerRepository questionAnswerRepository;

	@Override
	public List<AnswerData> getSurveyAnswerList(String surveyId, SurveyAnswerListGetReq surveyAnswerListGetReq) {
		Survey survey = Survey.builder().surveyId(surveyId).build();
		
		List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllBySurvey(survey);
		
		List<AnswerData> answerDataList = new ArrayList<>();
		
		List<FilterDataReq> filterDataList = surveyAnswerListGetReq.getFilterDataList();

		System.out.println(surveyAnswerListGetReq.getFilterDataList().get(0));

		List<String> categoryList = new ArrayList<>();
		Map<String, Double> categoryScoreMap = new HashMap<>();
		Map<String, Integer> categoryCountMap = new HashMap<>();
		Map<String, Map<String, Double>> categoryQuestionScoreMap = new HashMap<>();
		Map<String, Map<String, int[]>> categoryQuestionCountMap = new HashMap<>();
		
		for (QuestionAnswer questionAnswer : questionAnswerList) {
			FilterData filterData = questionAnswer.getFilterData();
			String filterRes = filterData.getResponse();
			
			JSONParser jsonParse = new JSONParser();

			System.out.println(filterDataList.size());
			
			try {
				JSONObject jsonObj =  (JSONObject) jsonParse.parse(filterRes);
				
//				int size = jsonObj.size();
//				int count = 0;
				System.out.println(jsonObj.toJSONString());
				
				boolean isCorrect = true;

				if(filterDataList != null) {
					for (FilterDataReq filterDataReq : filterDataList) {
						String filterKind = filterDataReq.getFilterKind();
						List<String> filterValue = filterDataReq.getFilterValue();

						System.out.println(filterKind+" "+jsonObj.get(filterKind)+" "+!filterValue.contains(jsonObj.get(filterKind)));
						
						if(!filterValue.contains(jsonObj.get(filterKind))) {
							isCorrect = false;
							break;
						}
						
//						count++;
					}
				}

				System.out.println(isCorrect);
				
				if(isCorrect /*&& count == size*/){
					String cateogryName = questionAnswer.getQuestion().getCategory().getName();

					//해당 문제의 content데이터
					Question question = questionAnswer.getQuestion();
					String questionContent = question.getContent();
//					System.out.println(questionContent);
					JSONObject questionJsonObj =  (JSONObject) jsonParse.parse(questionContent);
					int contentCount = questionJsonObj.size();

//					System.out.println("question content count: " + contentCount);

					//해당 응답의 데이터
					String answerRes = questionAnswer.getResponse();
					JSONObject answerJsonObj = (JSONObject) jsonParse.parse(answerRes);

//					System.out.println(questionAnswer.getFilterData().getResponse());

					//응답 데이터 저장 형태에 따라서 조정 필요
					String answer = (String) answerJsonObj.get("0");

//					System.out.println(answer);

					//선택한 답변의 번호
					int num = 0;

					for(int i = 1; i <= contentCount; i++){
						String idx = Integer.toString(i);
						String contentStr = (String) questionJsonObj.get(idx);

						if(answer.equals(contentStr)){
//							System.out.println(contentStr);
							num = i;
							break;
						}
					}

					double score = (5.0/(double)contentCount) * (double) num;

//					System.out.println(5.0/4);
//					System.out.println(contentCount+" "+score+" "+num);

					score = Math.round(score*100)/100.0;

//					System.out.println(num+" "+score);

					if(!categoryList.contains(cateogryName))	categoryList.add(cateogryName);

					if (categoryScoreMap.containsKey(cateogryName)) {
						double categryScore = categoryScoreMap.get(cateogryName);
						categoryScoreMap.put(cateogryName, categryScore + score);

						//있는 문제인 경우
						if(categoryQuestionScoreMap.get(cateogryName).containsKey(question.getTitle())){
							if(categoryQuestionScoreMap.get(cateogryName).containsKey(question.getTitle())){
								double questionScore = categoryQuestionScoreMap.get(cateogryName).get(question.getTitle());
								categoryQuestionScoreMap.get(cateogryName).put(question.getTitle(),questionScore + score);
							}
							else{
								categoryQuestionScoreMap.get(cateogryName).put(question.getTitle(),score);
							}

						}
						else{
							categoryQuestionScoreMap.get(cateogryName).put(question.getTitle(), score);
						}
					}
					else{
						categoryQuestionScoreMap.put(cateogryName, new HashMap<>());
						categoryQuestionScoreMap.get(cateogryName).put(question.getTitle(), score);
						categoryScoreMap.put(cateogryName,score);
					}

					if (categoryCountMap.containsKey(cateogryName)) {
						categoryCountMap.put(cateogryName, categoryCountMap.get(cateogryName) + 1);

						if (categoryQuestionCountMap.containsKey(cateogryName)) {
							if(categoryQuestionCountMap.get(cateogryName).containsKey(question.getTitle())){
								int[] questionCount = categoryQuestionCountMap.get(cateogryName).get(question.getTitle());
								questionCount[0] += 1;
//								categoryQuestionCountMap.get(cateogryName).put(question.getTitle(), questionCount + 1);
							}
							else{
								categoryQuestionCountMap.get(cateogryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
							}
						}
						else{
							categoryQuestionCountMap.get(cateogryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
						}
					}
					else{
						categoryQuestionCountMap.put(cateogryName, new HashMap<>());
						categoryQuestionCountMap.get(cateogryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
						categoryCountMap.put(cateogryName, 1);
					}
//					answerDataList.add(SurveyAnswer.builder()
//							.questionId(questionAnswer.getQuestion().getQuestionId())
//							.response(questionAnswer.getResponse())
//							.build());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		//여기에 데이터 저장 코드
		for (String categoryName : categoryList) {
			double categoryTotalScore = categoryScoreMap.get(categoryName);
			int count = categoryCountMap.get(categoryName);

			double resultScore = Math.round((categoryTotalScore/count)*100)/100.0;

			List<QuestionData> questionDataList = new ArrayList<QuestionData>();

			Map <String, Double> questionScoreMap = categoryQuestionScoreMap.get(categoryName);
			Map <String, int[]> questionCountMap = categoryQuestionCountMap.get(categoryName);

			List<String> questionTitleList = new ArrayList<>(questionScoreMap.keySet());

			for (String questionTitle : questionTitleList) {
				double questionScore = questionScoreMap.get(questionTitle);
				int[] questionCount = questionCountMap.get(questionTitle);

				double questionAverageScore = Math.round((questionScore/questionCount[0])*100)/100.0;

				QuestionData questionData = QuestionData.builder().title(questionTitle).averageScore(questionAverageScore).number(questionCount[1]).build();

				questionDataList.add(questionData);
			}

			AnswerData answerData = AnswerData.builder().categoryName(categoryName).averageScore(resultScore).questionDataList(questionDataList).build();

			answerDataList.add(answerData);
		}
		
		return answerDataList;
	}

}
