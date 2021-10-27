package com.ssurbar.survey.db.repository.survey;

import com.ssurbar.survey.db.entity.survey.Survey;
import com.ssurbar.survey.db.entity.survey.SurveyTarget;
import com.ssurbar.survey.db.entity.survey.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, String> {

	List<Survey> findAllByTemplate(Template template);
	
//	List<Survey> findAllByUserId(User user);

	SurveyTarget findBySurveyId(Survey survey);

}
