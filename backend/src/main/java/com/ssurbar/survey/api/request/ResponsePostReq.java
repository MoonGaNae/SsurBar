package com.ssurbar.survey.api.request;

import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

