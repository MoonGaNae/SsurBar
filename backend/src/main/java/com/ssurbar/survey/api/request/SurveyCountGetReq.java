package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyCountGetReq")
public class SurveyCountGetReq {
	@ApiModelProperty(name="surveyId", example="")	
	private String surveyId;
}
