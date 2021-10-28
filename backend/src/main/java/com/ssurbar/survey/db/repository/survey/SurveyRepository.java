package com.ssurbar.survey.db.repository.survey;

import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, String> {

	List<Survey> findAllByTemplate(Template template);

	Optional<Survey> findBySurveyId(String surveyId);
}
