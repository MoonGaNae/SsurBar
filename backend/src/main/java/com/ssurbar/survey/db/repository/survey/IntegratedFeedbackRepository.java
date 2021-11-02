package com.ssurbar.survey.db.repository.survey;

import com.ssurbar.survey.db.entity.survey.IntegratedFeedback;
import com.ssurbar.survey.db.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegratedFeedbackRepository extends JpaRepository<IntegratedFeedback,String> {

    Optional<IntegratedFeedback> findIntegratedFeedbackBySurveyId(String surveyId);

}
