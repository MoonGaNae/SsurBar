package com.ssurbar.survey.db.repository.survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;

@Repository
public interface SurveyResponseLogRepository extends JpaRepository<SurveyResponseLog, String>{

	List<SurveyResponseLog> findAllBySurvey(Survey survey);

}
