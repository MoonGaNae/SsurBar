package com.ssurbar.survey.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

@Getter
@Setter
@Builder
public class SurveyAnswer {
	private String questionId;
	private String filterResponse;
	private String response;
}
