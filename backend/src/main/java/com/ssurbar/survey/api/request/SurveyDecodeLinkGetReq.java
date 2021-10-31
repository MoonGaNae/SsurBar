package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("SurveyDecodeLinkGetReq")
public class SurveyDecodeLinkGetReq {
    private String linkCode;
    private String type;
}
