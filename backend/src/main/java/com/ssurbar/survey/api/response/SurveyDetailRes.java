package com.ssurbar.survey.api.response;

import java.util.Date;

import com.ssurbar.survey.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyDetailRes")
public class SurveyDetailRes extends BaseResponseBody{
	private String surveyId;
	private String templateId;
	private Date creationTime;
	private Date endTime;
	private String responseUrl;
	private String resultUrl;
	private String teamId;
	private String teamName;
}
