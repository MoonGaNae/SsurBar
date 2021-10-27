package com.ssurbar.survey.api.response;

import java.util.List;

import com.ssurbar.survey.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("SurveyCountRes")
public class SurveyAnswerRes extends BaseResponseBody {
	List<SurveyAnswer> surveyAnswerList;
}
