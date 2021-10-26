package com.ssurbar.db.repository.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.db.entity.survey.Survey;
import com.ssurbar.db.entity.survey.SurveyTarget;

@Repository
public interface SurveyTargetRepository extends JpaRepository<SurveyTarget, String>{

	SurveyTarget findBySurveyId(Survey survey);

}
