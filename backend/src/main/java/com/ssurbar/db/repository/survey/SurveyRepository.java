package com.ssurbar.db.repository.survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.db.entity.survey.Survey;
import com.ssurbar.db.entity.survey.SurveyForm;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, String> {

	List<Survey> findAllBySurveyFormId(SurveyForm surveyForm);

}
