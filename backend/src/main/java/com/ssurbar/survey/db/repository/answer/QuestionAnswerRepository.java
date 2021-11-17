package com.ssurbar.survey.db.repository.answer;


import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, String> {

    List<QuestionAnswer> findAllBySurvey(Survey survey);

    @Query(value =
        "SELECT " + "qa.filter_data_id " +
                "FROM Question_Answer qa " +
                "WHERE qa.survey_id = :surveyId " +
                "GROUP BY qa.filter_data_id", nativeQuery = true
    )
    List<String> findGroupByQuestionAnswerWithJPQL(String surveyId);
}

