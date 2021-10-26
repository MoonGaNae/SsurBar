package com.ssurbar.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyCountGetReq")
public class SurveyCountGetReq {
	@ApiModelProperty(name="surveyId", example="")	
	private String surveyId;
}
