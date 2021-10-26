package com.ssurbar.db.repository.answer;


import com.ssurbar.db.entity.answer.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, String> {
}
