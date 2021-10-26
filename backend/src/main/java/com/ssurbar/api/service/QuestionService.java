package com.ssurbar.api.service;

import com.ssurbar.db.entity.survey.Question;

public interface QuestionService {
    Question findByQuestionId(String questionId);
}
