package com.ssurbar.api.response;

import com.ssurbar.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyCountRes")
public class SurveyCountRes extends BaseResponseBody{
	private int count;
}
