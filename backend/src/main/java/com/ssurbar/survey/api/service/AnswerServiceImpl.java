package com.ssurbar.survey.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssurbar.survey.api.request.FilterDataReq;
import com.ssurbar.survey.api.response.*;
import com.ssurbar.survey.common.model.common.CategoryAnswerInfo;
import com.ssurbar.survey.common.model.common.QuestionAnswerInfo;
import com.ssurbar.survey.db.entity.answer.FilterData;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.repository.answer.FilterDataRepository;
import com.ssurbar.survey.db.repository.answer.QuestionAnswerRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService{

	@Autowired
	QuestionAnswerRepository questionAnswerRepository;

	@Autowired
	FilterDataRepository filterDataRepository;

	@Override
	public SurveyResultRes getSurveyAnswerList(String surveyId, String filterDataStr) throws JsonProcessingException, UnsupportedEncodingException {
		Survey survey = Survey.builder().surveyId(surveyId).build();

		List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAllBySurvey(survey);

		List<AnswerData> answerDataList = new ArrayList<>();

		String filterStr = URLDecoder.decode(filterDataStr,"UTF-8");

		//분석 페이지에서 선택한 필터
		List<FilterDataReq> filterDataList = Arrays.asList(new ObjectMapper().readValue(filterStr, FilterDataReq[].class));

		List<String> categoryList = new ArrayList<>();
		List<Integer> categoryNumberList = new ArrayList<>();
		//필터로 걸러진 후 해당 카테고리 정보
		Map<String, CategoryAnswerInfo> categoryMap = new HashMap<>();

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
					int categoryNum = questionAnswer.getQuestion().getCategory().getNumber();

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

					if(!categoryList.contains(categoryName)) {
						categoryNumberList.add(categoryNum);
						categoryList.add(categoryName);
					}

					//있는 카테고리인 경우
					if (categoryMap.containsKey(categoryName)) {
						CategoryAnswerInfo categoryAnswerInfo = categoryMap.get(categoryName);
						double categryScore = categoryAnswerInfo.getTotalScore();
						categoryAnswerInfo.setTotalScore(categryScore + score);
						categoryAnswerInfo.getScoreList().add(score);

						//있는 문제인 경우 -> 점수 갱신
//						if(categoryAnswerInfo.getQuestionMap().containsKey(question.getTitle())){
						if(categoryAnswerInfo.getQuestionMap().containsKey(question.getQuestionNum())){

//							QuestionAnswerInfo questionAnswerInfo = categoryAnswerInfo.getQuestionMap().get(question.getTitle());
							QuestionAnswerInfo questionAnswerInfo = categoryAnswerInfo.getQuestionMap().get(question.getQuestionNum());
							questionAnswerInfo.setTotalScore(questionAnswerInfo.getTotalScore() + score);
							questionAnswerInfo.getScoreList().add(score);

							//있는 답변인 경우
							if(questionAnswerInfo.getCountMap().containsKey(answer)){
								questionAnswerInfo.getCountMap().put(answer, questionAnswerInfo.getCountMap().get(answer) + 1);
							}
							else{
								questionAnswerInfo.getCountMap().put(answer, 1);
							}
						}
						else{
							Map<String, Integer> countMap = new HashMap<>();

							for(int i = 1; i <= contentCount; i++){
								String idx = Integer.toString(i);
								String contentStr = (String) questionJsonObj.get(idx);
								countMap.put(contentStr, 0);
							}

							countMap.put(answer,1);

							List<Double> questionScoreList = new ArrayList<>();
							questionScoreList.add(score);
							QuestionAnswerInfo questionAnswerInfo = QuestionAnswerInfo.builder()
									.totalScore(score)
									.scoreList(questionScoreList)
									.number(question.getQuestionNum())
									.title(question.getTitle())
									.countMap(countMap)
									.build();

							categoryAnswerInfo.getQuestionMap().put(question.getQuestionNum(), questionAnswerInfo);
						}
					}
					else{
						List<Double> categoryScoreList = new ArrayList<>();
						categoryScoreList.add(score);

						Map<String, Integer> countMap = new HashMap<>();

						for(int i = 1; i <= contentCount; i++){
							String idx = Integer.toString(i);
							String contentStr = (String) questionJsonObj.get(idx);
							countMap.put(contentStr, 0);
						}

						countMap.put(answer,1);

						Map<Integer, QuestionAnswerInfo> questionMap = new HashMap<>();
						List<Double> questionScoreList = new ArrayList<>();
						questionScoreList.add(score);
						QuestionAnswerInfo questionAnswerInfo = QuestionAnswerInfo.builder()
								.totalScore(score)
								.scoreList(questionScoreList)
								.number(question.getQuestionNum())
								.title(question.getTitle())
								.countMap(countMap)
								.build();

						questionMap.put(question.getQuestionNum(), questionAnswerInfo);

						CategoryAnswerInfo categoryAnswerInfo = CategoryAnswerInfo
								.builder()
								.totalScore(score)
								.scoreList(categoryScoreList)
								.questionMap(questionMap)
								.build();

						categoryMap.put(categoryName, categoryAnswerInfo);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		List<QuestionData> totalQuestionDataList = new ArrayList<>();

		int idx = 0;
		//문항, 카테고리별 평균 계산
		for (String categoryName : categoryList) {
			CategoryAnswerInfo categoryAnswerInfo = categoryMap.get(categoryName);
			int categoryNum = categoryNumberList.get(idx++);
			double categoryTotalScore = categoryAnswerInfo.getTotalScore();
			int count = categoryAnswerInfo.getScoreList().size();
			double caregoryVariance = 0;

			double categoryAverageScore = Math.round((categoryTotalScore/count)*100)/100.0;

			for (Double score: categoryAnswerInfo.getScoreList()){
				caregoryVariance += Math.pow(Math.abs(score - categoryAverageScore),2);
			}

			caregoryVariance = caregoryVariance/count;

			double categoryStandardDeviation = Math.round(Math.sqrt(caregoryVariance)*100)/100.0;

			List<QuestionData> questionDataList = new ArrayList<QuestionData>();

			Map <Integer, QuestionAnswerInfo> questionMap = categoryAnswerInfo.getQuestionMap();

			List<Integer> questionNumberList = new ArrayList<>(questionMap.keySet());

			for (int questionNumber : questionNumberList) {
				QuestionAnswerInfo questionAnswerInfo = questionMap.get(questionNumber);
				double questionScore = questionAnswerInfo.getTotalScore();
				int questionCount = questionAnswerInfo.getScoreList().size();
				String questionTitle = questionAnswerInfo.getTitle();
				//문항 평균
				double questionAverageScore = Math.round((questionScore/questionCount)*100)/100.0;
				double questionVariance = 0;

				//문항별 분산을 계산
				for (Double score: questionAnswerInfo.getScoreList()){
					questionVariance += Math.pow(Math.abs(score - questionAverageScore),2);
				}

				questionVariance = questionVariance/count;

				//문항별 표준편차
				double questionStandardDeviation = Math.round(Math.sqrt(questionVariance)*100)/100.0;

				Map<String, Integer> countMap = questionAnswerInfo.getCountMap();

				List<QuestionData.QuestionAnswerDto> questionAnswerDtoList = new ArrayList<>();

				for (String key :countMap.keySet()){
					int selectedCount = countMap.get(key);
					double selectedPercentage = (double)Math.round(((double)selectedCount/questionCount)*10000)/100;

					QuestionData.QuestionAnswerDto questionAnswerDto = QuestionData.QuestionAnswerDto.builder()
							.count(selectedCount)
							.percentage(selectedPercentage)
							.sentence(key)
							.build();

					questionAnswerDtoList.add(questionAnswerDto);
				}

				QuestionData questionData = QuestionData.builder()
						.title(questionTitle)
						.averageScore(questionAverageScore)
						.number(questionNumber)
						.standardDeviation(questionStandardDeviation)
						.questionAnswerDtoList(questionAnswerDtoList)
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
					.categoryNum(categoryNum)
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

		//최저 평균
		double lowestAverageDataLimit = 0;
		for (int i = 0; i < totalQuestionDataList.size(); i++){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i >= 3 && questionData.getAverageScore() != lowestAverageDataLimit){
				break;
			}
			lowestAverageDataLimit = questionData.getAverageScore();

			lowestAverageList.add(questionData);
		}
		//최고 평균
		double highestAverageDataLimit = 0;
		for (int i = totalQuestionDataList.size()-1; i >= 0; i--){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i <= totalQuestionDataList.size()-4 && questionData.getAverageScore() != highestAverageDataLimit){
				break;
			}
			highestAverageDataLimit = questionData.getAverageScore();
			highestAverageList.add((totalQuestionDataList.get(i)));
		}
		Collections.sort(totalQuestionDataList,((o1, o2) -> {
			return Double.compare(o1.getStandardDeviation(),o2.getStandardDeviation());
		}));

		//최저 편차
		double lowestStandardDeviationDataLimit = 0;
		for (int i = 0; i < totalQuestionDataList.size(); i++){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i >= 3 && questionData.getStandardDeviation() != lowestStandardDeviationDataLimit){
				break;
			}
			lowestStandardDeviationDataLimit = questionData.getStandardDeviation();
			lowestStandardDeviationList.add(totalQuestionDataList.get(i));
		}
		//최고 편차
		double hightestStandardDeviationDataLimit = 0;
		for (int i = totalQuestionDataList.size()-1; i >= 0 ; i--){
			QuestionData questionData = totalQuestionDataList.get(i);
			if(i <= totalQuestionDataList.size()-4 && questionData.getStandardDeviation() != hightestStandardDeviationDataLimit){
				break;
			}
			hightestStandardDeviationDataLimit = questionData.getStandardDeviation();
			highestStandardDeviationList.add(totalQuestionDataList.get(i));
		}

		Collections.sort(answerDataList, ((o1, o2) -> {
			return Integer.compare(o1.getCategoryNum(), o2.getCategoryNum());
		}));

		for (AnswerData ad:
		answerDataList) {
			System.out.println(ad.getCategoryNum());
		}

		SurveyResultRes surveyResultRes = SurveyResultRes.builder()
				.questionCount(totalQuestionDataList.size())
				.answerDataList(answerDataList)
				.highestStandardDeviationList(highestStandardDeviationList)
				.highestAverageList(highestAverageList)
				.lowestStandardDeviationList(lowestStandardDeviationList)
				.lowestAverageList(lowestAverageList)
				.build();

		return surveyResultRes;
	}

	@Override
	public SurveyAnswerRawGetRes getSurveyAnswerRaw(String surveyId) {
		List<String> filterDataIdList = questionAnswerRepository.findGroupByQuestionAnswerWithJPQL();

//		for(String id : filterDataIdList){
//			System.out.println(id);
//		}

		List<FilterData> filterDataList = filterDataRepository.findByFilterDataIdIn(filterDataIdList);

		List<FilterDataDto> filterDataDtoList = new ArrayList<>();
		for(FilterData data : filterDataList){
			System.out.println("사람" + data.getFilterDataId());
			List<QuestionAnswer> questionAnswerList = data.getQuestionAnswers();

			filterDataDtoList.add(
					FilterDataDto.builder()
							.response(data.getResponse())
							.questionAnswers(
									questionAnswerList.stream()
											.map(FilterDataDto.QuestionAnswerDto::of)
											.collect(Collectors.toList())
							)
							.build()
			);
		}

		System.out.println(filterDataDtoList);

		return SurveyAnswerRawGetRes.builder()
				.filterDatas(filterDataDtoList)
				.build();
	}


}
