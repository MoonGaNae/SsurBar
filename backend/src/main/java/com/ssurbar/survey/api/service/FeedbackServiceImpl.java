package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.FeedBackPatchReq;
import com.ssurbar.survey.api.request.FeedBackPostReq;
import com.ssurbar.survey.db.entity.survey.IntegratedFeedback;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.repository.survey.IntegratedFeedbackRepository;
import com.ssurbar.survey.db.repository.survey.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    IntegratedFeedbackRepository feedbackRepository;

    @Override
    public IntegratedFeedback addTotalFeedback(FeedBackPostReq feedBackPostReq) {
        String surveyId = feedBackPostReq.getSurveyId();
        Optional<Survey> survey = surveyRepository.findBySurveyId(surveyId);
        String comment = feedBackPostReq.getComment();

        IntegratedFeedback feedback = IntegratedFeedback.builder()
                .survey(survey.get())
                .surveyId(surveyId)
                .comment(comment)
                .build();

        feedbackRepository.save(feedback);

        return feedback;

    }

    @Override
    public IntegratedFeedback editTotalFeedback(String surveyId, FeedBackPatchReq feedBackPatchReq) {
        Optional<Survey> survey = surveyRepository.findBySurveyId(surveyId);

        IntegratedFeedback feedback = feedbackRepository.findIntegratedFeedbackBySurveyId(surveyId).get();
        if(feedback.getComment()!=null){
            feedbackRepository.delete(feedback);
            IntegratedFeedback newFeedback = IntegratedFeedback.builder()
                    .survey(survey.get())
                    .surveyId(surveyId)
                    .comment(feedBackPatchReq.getComment())
                    .build();

            feedbackRepository.save(newFeedback);

            return newFeedback;

        }
        return null;
    }


    @Override
    public IntegratedFeedback getTotalFeedback(String surveyId) {
        Optional<IntegratedFeedback> feedback = feedbackRepository.findIntegratedFeedbackBySurveyId(surveyId);

        if(feedback.isPresent()){
            return feedback.get();
        }
        return null;
    }

    @Override
    public void deleteTotalFeedback(String surveyId) {
        Optional<IntegratedFeedback> feedback = feedbackRepository.findIntegratedFeedbackBySurveyId(surveyId);

        if(feedback.isPresent()){
            feedbackRepository.delete(feedback.get());
        }else{
            System.out.println("지울 피드백이 없습니다!");
        }
    }
}
