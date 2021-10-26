package com.ssurbar.db.repository.survey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.db.entity.survey.SurveyResponseLog;
import com.ssurbar.db.entity.survey.SurveyTarget;

@Repository
public interface SurveyResponseLogRepository extends JpaRepository<SurveyResponseLog, String>{

	List<SurveyResponseLog> findAllBySurveyTargetId(SurveyTarget surveyTarget);

}
