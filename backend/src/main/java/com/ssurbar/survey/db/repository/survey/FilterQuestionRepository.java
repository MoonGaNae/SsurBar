package com.ssurbar.survey.db.repository.survey;

import com.ssurbar.survey.db.entity.survey.FilterQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterQuestionRepository extends JpaRepository<FilterQuestion, String> {
}
