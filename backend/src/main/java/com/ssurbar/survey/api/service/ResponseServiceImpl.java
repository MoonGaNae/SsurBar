package com.ssurbar.survey.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.ssurbar.survey.api.request.ResponsePostReq;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.answer.FilterData;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.repository.answer.FilterDataRepository;
import com.ssurbar.survey.db.repository.answer.QuestionAnswerRepository;
import com.ssurbar.survey.db.repository.survey.QuestionRepository;
import com.ssurbar.survey.db.repository.survey.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("responseService")
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    FilterDataRepository filterDataRepository;

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
    public List<QuestionAnswer> saveAnswer(ResponsePostReq responsePostReq) {
        String surveyId = responsePostReq.getSurveyId();
        String filterAnswer = responsePostReq.getFilterAnswer();
        List<String> answerList = responsePostReq.getAnswerList();
        JsonParser jsonParser = new JsonParser();
        List<QuestionAnswer> list = new ArrayList<>();

        System.out.println(responsePostReq.getAnswerList());
        System.out.println(filterAnswer);

        //filterAnswer = responsePostReq.getFilterAnswer();
        //answerList = responsePostReq.getAnswerList();

        FilterData filterData = FilterData.builder()
                .filterDataId(randomIdUtil.makeRandomId(13))
                .response(filterAnswer)
                .build();

        filterDataRepository.save(filterData);

        for ( String content : answerList ) {
            System.out.println(100);

            /**
             * answer의 형태
             * { "questionId": String,
             *  "answer": JSON }
             */

            JsonElement element = jsonParser.parse(content);
            String questionId = element.getAsJsonObject().get("questionId").getAsString();
            String answer = element.getAsJsonObject().get("answer").getAsString();
            // test
            Question q = new Question();


            QuestionAnswer questionAnswer = QuestionAnswer.builder()
                    .questionAnswerId(randomIdUtil.makeRandomId(13))
                    //.question(questionRepository.findQuestionByQuestionId(questionId).get())
                    .question(q)
                    .response(answer)
                    .filterData(filterData)
                    .survey(surveyRepository.findBySurveyId(surveyId).get())
                    .build();

            System.out.println(questionAnswer.getResponse());
            questionAnswerRepository.save(questionAnswer);
            list.add(questionAnswer);
        }

        return list;

    }

}

