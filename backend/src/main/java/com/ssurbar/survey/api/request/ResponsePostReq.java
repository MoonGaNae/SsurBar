package com.ssurbar.survey.api.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ResponseReq")
public class ResponsePostReq {
    @ApiModelProperty
    String surveyId;

    @ApiModelProperty
    String filterAnswer;

    @ApiModelProperty
    List<String> answerList;
}

