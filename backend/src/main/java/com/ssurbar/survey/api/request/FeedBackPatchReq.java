package com.ssurbar.survey.api.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("FeedBackPatchRequest")
public class FeedBackPatchReq {
    private String comment;
}
