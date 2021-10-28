package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TemplatePostReq")
@Builder
public class TemplatePostReq {
    @ApiModelProperty(name="title", example="")
    private String title;
    @ApiModelProperty(name="desc", example="")
    private String description;
}
