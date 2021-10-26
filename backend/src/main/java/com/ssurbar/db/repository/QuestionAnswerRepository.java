package com.ssurbar.db.repository;


import com.ssurbar.db.entity.answer.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, String> {
}
