package com.ssurbar.survey.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class RecentSurveyInfo {
    private Date creationTime;
    private Date endTime;
    private String surveyId;
    private String title;
    private String teamName;
}
