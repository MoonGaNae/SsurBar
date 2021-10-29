package com.ssurbar.survey.api.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("SurveyCreatePostReq")
public class SurveyCreatePostReq {
	@ApiModelProperty(name="templateId", example="")	
	private String templateId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name="creationTime", example="")
	private Date creationTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(name="endTime", example="")
	private Date endTime;
	
	@ApiModelProperty(name="teamId", example="")
	private String teamId;
}
