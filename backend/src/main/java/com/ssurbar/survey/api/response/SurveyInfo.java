package com.ssurbar.survey.api.response;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SurveyInfo {
	private Date creationTime;
	private Date endTime;
	private String surveyId;
	private String title;
	private String teamName;
}
