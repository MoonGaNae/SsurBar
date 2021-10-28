package com.ssurbar.survey.db.entity.survey;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssurbar.survey.db.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseLog {
	@Id
	private String surveyResponseLogId;

	private LocalDateTime responseTime;
	
    @ManyToOne
    @JoinColumn(name = "survey_id")
	private Survey survey;
}
