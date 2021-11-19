package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
public class SurveyDecodeLinkGetRes extends BaseResponseBody {
    private String surveyId;
    private String templateId;
}
