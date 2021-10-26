package com.ssurbar.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssurbar.api.request.ResponsePostReq;
import com.ssurbar.db.entity.answer.FilterData;
import com.ssurbar.db.entity.answer.QuestionAnswer;
import com.ssurbar.db.repository.FilterDataRepository;
import com.ssurbar.db.repository.QuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("responseService")
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    FilterDataRepository filterDataRepository;

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    /* 사용자의 응답을 수집하여 DB에 저장하기 */
    @Override
    public void saveAnswer(ResponsePostReq responsePostReq) {
        String surveyId = responsePostReq.getSurveyId();
        String filterAnswer = responsePostReq.getFilterAnswer();
        List<String> answerList = responsePostReq.getAnswerList();

        filterAnswer = responsePostReq.getFilterAnswer();
        answerList = responsePostReq.getAnswerList();

//        FilterData filterData = FilterData.builder().
//        QuestionAnswer questionAnswer = QuestionAnswer.builder().
//        questionAnswerRepository.save();
    }

}