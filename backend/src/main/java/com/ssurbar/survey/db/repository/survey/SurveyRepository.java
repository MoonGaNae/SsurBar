package com.ssurbar.survey.db.repository.survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyForm;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, String> {

	List<Survey> findAllBySurveyForm(SurveyForm surveyForm);
	
//	List<Survey> findAllByUserId(User user);

	SurveyTarget findBySurveyId(Survey survey);

}
