package com.ssurbar.survey.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilterDataReq {
	private String filterKind;
	private String filterValue;
}
