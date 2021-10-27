package com.ssurbar.survey.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyCreatePostReq")
public class SurveyCreatePostReq {
	@ApiModelProperty(name="surveyFormId", example="")	
	private String surveyFormId;
	@ApiModelProperty(name="creationTime", example="")
	private Date creationTime;
	@ApiModelProperty(name="endTime", example="")
	private Date endTime;
}
