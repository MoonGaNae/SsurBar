package com.ssurbar.api.service;

import com.ssurbar.db.entity.survey.Question;
import com.ssurbar.db.repository.survey.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Question findByQuestionId(String questionId) {
        Optional<Question> question = questionRepository.findQuestionByQuestionId(questionId);
        if(question.isPresent()) {
            return question.get();
        }
        return null;

    }
}
