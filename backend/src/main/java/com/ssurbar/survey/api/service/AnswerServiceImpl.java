package com.ssurbar.survey.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssurbar.survey.api.request.FilterDataReq;
import com.ssurbar.survey.api.response.AnswerData;
import com.ssurbar.survey.api.response.QuestionData;
import com.ssurbar.survey.api.response.SurveyResultRes;
import com.ssurbar.survey.common.model.common.CategoryAnswerInfo;
import com.ssurbar.survey.common.model.common.QuestionAnswerInfo;
import com.ssurbar.survey.db.entity.answer.FilterData;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.repository.answer.QuestionAnswerRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	QuestionAnswerRepository questionAnswerRepository;

	@Override
	public SurveyResultRes getSurveyAnswerList(String surveyId, String filterDataStr) throws JsonProcessingException, UnsupportedEncodingException {
		Survey survey = Survey.builder().surveyId(surveyId).build();

		List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllBySurvey(survey);

		List<AnswerData> answerDataList = new ArrayList<>();

//		System.out.println(questionAnswerList.size());

		String filterStr = URLDecoder.decode(filterDataStr,"UTF-8");

		//분석 페이지에서 선택한 필터
		List<FilterDataReq> filterDataList = Arrays.asList(new ObjectMapper().readValue(filterStr, FilterDataReq[].class));

		//각 카테고리에 해당, 문항에 해당하는 각각의 점수도 저장필요
		List<String> categoryList = new ArrayList<>();
		//필터로 걸러진 후 해당 카테고리 응답 점수의 합
//		Map<String, Double> categoryScoreMap = new HashMap<>();
		Map<String, CategoryAnswerInfo> categoryMap = new HashMap<>();
		//필터로 걸러진 후 해당 카테고리 응답의 수
//		Map<String, Integer> categoryCountMap = new HashMap<>();
//		Map<String, Map<String, Double>> categoryQuestionScoreMap = new HashMap<>();
//		Map<String, Map<String, int[]>> categoryQuestionCountMap = new HashMap<>();

		//해당 설문의 모든 응답에 대해서
		for (QuestionAnswer questionAnswer : questionAnswerList) {
			FilterData filterData = questionAnswer.getFilterData();
			String filterRes = filterData.getResponse();

			JSONParser jsonParse = new JSONParser();

			try {
				JSONObject jsonObj =  (JSONObject) jsonParse.parse(filterRes);

				boolean isCorrect = true;

				//필터가 없는 경우
				if(filterDataList != null) {
					for (FilterDataReq filterDataReq : filterDataList) {
						String filterKind = filterDataReq.getFilterKind();
						List<String> filterValue = filterDataReq.getFilterValue();

						//필터 선택 안한 경우
						if(filterValue.size()==0){
							continue;
						}
						//선택한 필터에 없는 경우
						if(!filterValue.contains(jsonObj.get(filterKind))) {
							isCorrect = false;
							break;
						}
					}
				}

				//선택한 데이터인 경우
				if(isCorrect){
					String categoryName = questionAnswer.getQuestion().getCategory().getName();

					//해당 문제의 content데이터
					Question question = questionAnswer.getQuestion();
					String questionContent = question.getContent();
					JSONObject questionJsonObj =  (JSONObject) jsonParse.parse(questionContent);
					int contentCount = questionJsonObj.size();

					//해당 응답의 데이터
					String answerRes = questionAnswer.getResponse();
					JSONObject answerJsonObj = (JSONObject) jsonParse.parse(answerRes);

					//응답 데이터 저장 형태에 따라서 조정 필요
					String answer = (String) answerJsonObj.get("0");

					//선택한 답변의 번호
					int num = 0;

					for(int i = 1; i <= contentCount; i++){
						String idx = Integer.toString(i);
						String contentStr = (String) questionJsonObj.get(idx);

						if(answer.equals(contentStr)){
							num = i;
							break;
						}
					}

					double score = (5.0/(double)contentCount) * (double) num;

					score = Math.round(score*100)/100.0;

					if(!categoryList.contains(categoryName))	categoryList.add(categoryName);

					//있는 카테고리인 경우
					if (categoryMap.containsKey(categoryName)) {
						CategoryAnswerInfo categoryAnswerInfo = categoryMap.get(categoryName);
						double categryScore = categoryAnswerInfo.getTotalScore();
						categoryAnswerInfo.setTotalScore(categryScore + score);
						categoryAnswerInfo.getScoreList().add(score);
//						categoryMap.put(categoryName, categryScore + score);

						//있는 문제인 경우 -> 점수 갱신
						if(categoryAnswerInfo.getQuestionMap().containsKey(question.getTitle())){
//						if(categoryQuestionScoreMap.get(categoryName).containsKey(question.getTitle())){
//							if(categoryQuestionScoreMap.get(categoryName).containsKey(question.getTitle())){
//								double questionScore = categoryQuestionScoreMap.get(categoryName).get(question.getTitle());
//								categoryQuestionScoreMap.get(categoryName).put(question.getTitle(),questionScore + score);
//							}
//							else{
//								categoryQuestionScoreMap.get(categoryName).put(question.getTitle(),score);
//							}

							QuestionAnswerInfo questionAnswerInfo = categoryAnswerInfo.getQuestionMap().get(question.getTitle());
							questionAnswerInfo.setTotalScore(questionAnswerInfo.getTotalScore() + score);
							questionAnswerInfo.getScoreList().add(score);
						}
						else{
							List<Double> questionScoreList = new ArrayList<>();
							questionScoreList.add(score);
							QuestionAnswerInfo questionAnswerInfo = QuestionAnswerInfo.builder()
									.totalScore(score)
									.scoreList(questionScoreList)
									.number(question.getQuestionNum())
									.build();
//							categoryQuestionScoreMap.get(categoryName).put(question.getTitle(), score);

							categoryAnswerInfo.getQuestionMap().put(question.getTitle(), questionAnswerInfo);
						}
					}
					else{
						List<Double> categoryScoreList = new ArrayList<>();
						categoryScoreList.add(score);

						Map<String, QuestionAnswerInfo> questionMap = new HashMap<>();
						List<Double> questionScoreList = new ArrayList<>();
						questionScoreList.add(score);
						QuestionAnswerInfo questionAnswerInfo = QuestionAnswerInfo.builder()
								.totalScore(score)
								.scoreList(questionScoreList)
								.number(question.getQuestionNum())
								.build();

						questionMap.put(question.getTitle(), questionAnswerInfo);

						CategoryAnswerInfo categoryAnswerInfo = CategoryAnswerInfo
								.builder()
								.totalScore(score)
								.scoreList(categoryScoreList)
								.questionMap(questionMap)
								.build();

//						categoryQuestionScoreMap.put(categoryName, new HashMap<>());
//						categoryQuestionScoreMap.get(categoryName).put(question.getTitle(), score);
						categoryMap.put(categoryName, categoryAnswerInfo);
					}

//					if (categoryCountMap.containsKey(categoryName)) {
//						categoryCountMap.put(categoryName, categoryCountMap.get(categoryName) + 1);
//
//						if (categoryQuestionCountMap.containsKey(categoryName)) {
//							if(categoryQuestionCountMap.get(categoryName).containsKey(question.getTitle())){
//								int[] questionCount = categoryQuestionCountMap.get(categoryName).get(question.getTitle());
//								questionCount[0] += 1;
//							}
//							else{
//								categoryQuestionCountMap.get(categoryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
//							}
//						}
//						else{
//							categoryQuestionCountMap.get(categoryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
//						}
//					}
//					else{
//						categoryQuestionCountMap.put(categoryName, new HashMap<>());
//						categoryQuestionCountMap.get(categoryName).put(question.getTitle(), new int[]{1, question.getQuestionNum()});
//						categoryCountMap.put(categoryName, 1);
//					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		List<QuestionData> totalQuestionDataList = new ArrayList<>();

		//문항, 카테고리별 평균 계산
		for (String categoryName : categoryList) {
			CategoryAnswerInfo categoryAnswerInfo = categoryMap.get(categoryName);
			double categoryTotalScore = categoryAnswerInfo.getTotalScore();
			int count = categoryAnswerInfo.getScoreList().size();
//			System.out.println("cateGoryCount:" +count);
//			System.out.println("categoryTotalScore:" +categoryTotalScore);
//			double caregoryVariance = categoryMap.get(categoryName).getVarianceBase()/count;
			double caregoryVariance = 0;

			double categoryAverageScore = Math.round((categoryTotalScore/count)*100)/100.0;

			for (Double score: categoryAnswerInfo.getScoreList()){
				caregoryVariance += Math.pow(Math.abs(score - categoryAverageScore),2);
			}

			caregoryVariance = caregoryVariance/count;

			double categoryStandardDeviation = Math.round(Math.sqrt(caregoryVariance)*100)/100.0;

			List<QuestionData> questionDataList = new ArrayList<QuestionData>();

			Map <String, QuestionAnswerInfo> questionMap = categoryAnswerInfo.getQuestionMap();
//			Map <String, int[]> questionCountMap = categoryQuestionCountMap.get(categoryName);

			List<String> questionTitleList = new ArrayList<>(questionMap.keySet());

			for (String questionTitle : questionTitleList) {
				QuestionAnswerInfo questionAnswerInfo = questionMap.get(questionTitle);
				double questionScore = questionAnswerInfo.getTotalScore();
				int questionCount = questionAnswerInfo.getScoreList().size();
				int questionNumber = questionAnswerInfo.getNumber();
				double questionAverageScore = Math.round((questionScore/questionCount)*100)/100.0;
//				System.out.println(questionAnswerInfo.getScoreList().size());
//				System.out.println(questionCount+" "+questionScore);

				double questionVariance = 0;

				for (Double score: questionAnswerInfo.getScoreList()){
					questionVariance += Math.pow(Math.abs(score - questionAverageScore),2);
				}

				questionVariance = questionVariance/count;

				double questionStandardDeviation = Math.round(Math.sqrt(questionVariance)*100)/100.0;

				QuestionData questionData = QuestionData.builder()
						.title(questionTitle)
						.averageScore(questionAverageScore)
						.number(questionNumber)
						.standardDeviation(questionStandardDeviation)
						.build();

				questionDataList.add(questionData);

				totalQuestionDataList.add(questionData);
			}

			Collections.sort(questionDataList, ((o1, o2) -> {
				return Integer.compare(o1.getNumber(), o2.getNumber());
			}));

			AnswerData answerData = AnswerData.builder()
					.categoryName(categoryName)
					.averageScore(categoryAverageScore)
					.questionDataList(questionDataList)
					.standardDeviation(categoryStandardDeviation)
					.build();

			answerDataList.add(answerData);
		}

		Collections.sort(totalQuestionDataList,((o1, o2) -> {
			return Double.compare(o1.getAverageScore(),o2.getAverageScore());
		}));

		List<QuestionData> lowestAverageList = new ArrayList<>();
		List<QuestionData> highestAverageList = new ArrayList<>();
		List<QuestionData> lowestStandardDeviationList = new ArrayList<>();
		List<QuestionData> highestStandardDeviationList = new ArrayList<>();

//		System.out.println("=====================================");
		double lowestAverageDataLimit = 0;
		for (int i = 0; i < totalQuestionDataList.size(); i++){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i >= 3 && questionData.getAverageScore() != lowestAverageDataLimit){
				break;
			}
			lowestAverageDataLimit = questionData.getAverageScore();

			lowestAverageList.add(questionData);
//			System.out.println(totalQuestionDataList.get(i).getAverageScore());
		}
//		System.out.println("---------------------------");
		double highestAverageDataLimit = 0;
		for (int i = totalQuestionDataList.size()-1; i >= 0; i--){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i <= totalQuestionDataList.size()-4 && questionData.getAverageScore() != highestAverageDataLimit){
				break;
			}
			highestAverageDataLimit = questionData.getAverageScore();
			highestAverageList.add((totalQuestionDataList.get(i)));
//			System.out.println(totalQuestionDataList.get(i).getAverageScore());
		}
//		System.out.println("=====================================");
		Collections.sort(totalQuestionDataList,((o1, o2) -> {
			return Double.compare(o1.getStandardDeviation(),o2.getStandardDeviation());
		}));

		double lowestStandardDeviationDataLimit = 0;
		for (int i = 0; i < totalQuestionDataList.size(); i++){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i >= 3 && questionData.getStandardDeviation() != lowestStandardDeviationDataLimit){
				break;
			}
			lowestStandardDeviationDataLimit = questionData.getStandardDeviation();
			lowestStandardDeviationList.add(totalQuestionDataList.get(i));
//			System.out.println(totalQuestionDataList.get(i).getStandardDeviation());
		}
//		System.out.println("---------------------------");
		double hightestStandardDeviationDataLimit = 0;
		for (int i = totalQuestionDataList.size()-1; i >= 0 ; i--){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i <= totalQuestionDataList.size()-4 && questionData.getStandardDeviation() != hightestStandardDeviationDataLimit){
				break;
			}
			hightestStandardDeviationDataLimit = questionData.getStandardDeviation();
			highestStandardDeviationList.add(totalQuestionDataList.get(i));
//			System.out.println(totalQuestionDataList.get(i).getStandardDeviation());
		}
//		System.out.println("=====================================");

		SurveyResultRes surveyResultRes = SurveyResultRes.builder()
				.answerDataList(answerDataList)
				.highestStandardDeviationList(highestStandardDeviationList)
				.highestAverageList(highestAverageList)
				.lowestStandardDeviationList(lowestStandardDeviationList)
				.lowestAverageList(lowestAverageList)
				.build();

		return surveyResultRes;
	}

}
