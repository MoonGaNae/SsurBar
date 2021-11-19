package com.ssurbar.survey.db.repository.survey;

import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyResponseLogRepository extends JpaRepository<SurveyResponseLog, String>{

	List<SurveyResponseLog> findAllBySurvey(Survey survey);

}
