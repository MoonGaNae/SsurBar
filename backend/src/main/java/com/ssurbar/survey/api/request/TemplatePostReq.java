package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ApiModel("TemplatePostReq")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplatePostReq {
    @ApiModelProperty(name="title", example="")
    private String title;
    @ApiModelProperty(name="desc", example="")
    private String description;
}
