package com.ssurbar.survey.db.repository.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssurbar.survey.db.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String>{

}
