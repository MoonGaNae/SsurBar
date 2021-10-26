package com.ssurbar.db.repository.survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.db.entity.answer.QuestionAnswer;
import com.ssurbar.db.entity.survey.Survey;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, String>{

	List<QuestionAnswer> findAllBySurveyId(Survey survey);

}
