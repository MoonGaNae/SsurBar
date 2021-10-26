package com.ssurbar.api.response;

import java.util.List;

import com.ssurbar.common.model.response.BaseResponseBody;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SurveyAnswerRes extends BaseResponseBody {
	List<SurveyAnswer> surveyAnswerList;
}
