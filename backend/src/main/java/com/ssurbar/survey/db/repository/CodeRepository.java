package com.ssurbar.survey.db.repository;

import com.ssurbar.survey.db.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, String> {
}
