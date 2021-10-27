package com.ssurbar.survey.api.response;

import java.util.List;

import com.ssurbar.survey.common.model.response.BaseResponseBody;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SurveyListRes extends BaseResponseBody{
	List<SurveyInfo> surveyList;
	

}
