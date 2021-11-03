package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("FeedBackPostRequest")
public class FeedBackPostReq {
    private String surveyId;
    private String comment;
}
