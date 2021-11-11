package com.ssurbar.survey.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssurbar.survey.api.request.ResponsePostReq;
import com.ssurbar.survey.api.response.SurveyAnswer;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.answer.FilterData;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.repository.answer.FilterDataRepository;
import com.ssurbar.survey.db.repository.answer.QuestionAnswerRepository;
import com.ssurbar.survey.db.repository.survey.QuestionRepository;
import com.ssurbar.survey.db.repository.survey.SurveyRepository;
import com.ssurbar.survey.db.repository.survey.SurveyResponseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("responseService")
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    FilterDataRepository filterDataRepository;

    @Autowired
    SurveyResponseLogRepository surveyResponseLogRepository;

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    RandomIdUtil randomIdUtil;

    /* 사용자의 응답을 수집하여 DB에 저장하기 */
    @Override
    public List<SurveyAnswer> saveAnswer(ResponsePostReq responsePostReq) {
        String surveyId = responsePostReq.getSurveyId();
        String filterAnswer = responsePostReq.getFilterAnswer();
        List<String> answerList = responsePostReq.getAnswerList();
        JsonParser jsonParser = new JsonParser();
        List<SurveyAnswer> list = new ArrayList<>();

        Survey survey = surveyRepository.findBySurveyId(surveyId).get();

        FilterData filterData = FilterData.builder()
                .filterDataId(randomIdUtil.makeRandomId(13))
                .response(filterAnswer)
                .build();

        filterDataRepository.save(filterData);

        for ( String content : answerList ) {
            
            /**
             * answer의 형태
             * { "questionId": String,
             *  "answer": JSON }
             */

            JsonElement element = jsonParser.parse(content);
            String questionId = element.getAsJsonObject().get("questionId").getAsString();
            String answer = element.getAsJsonObject().get("answer").getAsString();

            QuestionAnswer questionAnswer = QuestionAnswer.builder()
                    .questionAnswerId(randomIdUtil.makeRandomId(13))
                    .question(questionRepository.findQuestionByQuestionId(questionId).get())
                    .response("{\"0\":\""+answer+"\"}")
                    .filterData(filterDataRepository.findFilterDataByFilterDataId(filterData.getFilterDataId()).get())
                    .survey(survey)
                    .build();

            questionAnswerRepository.save(questionAnswer);
            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .questionId(questionAnswer.getQuestion().getQuestionId())
                    .filterResponse(questionAnswer.getFilterData().getResponse())
                    .response(questionAnswer.getResponse())
                    .build();

            list.add(surveyAnswer);

        }

        String surveyResponseLogId = randomIdUtil.makeRandomId(13);

        List<SurveyResponseLog> surveyResponseLogList = surveyResponseLogRepository.findAll();

        while(surveyResponseLogList.contains(surveyResponseLogId)){
            surveyResponseLogId = randomIdUtil.makeRandomId(13);
        }


        SurveyResponseLog surveyResponseLog = SurveyResponseLog.builder()
                .responseTime(LocalDateTime.now())
                .survey(survey)
                .surveyResponseLogId(surveyResponseLogId)
                .build();



        System.out.println(surveyResponseLogRepository.save(surveyResponseLog));

        return list;

    }

}

