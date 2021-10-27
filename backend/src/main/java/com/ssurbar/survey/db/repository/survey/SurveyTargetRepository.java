package com.ssurbar.survey.db.repository.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;

@Repository
public interface SurveyTargetRepository extends JpaRepository<SurveyTarget, String>{

	SurveyTarget findBySurvey(Survey survey);

}
