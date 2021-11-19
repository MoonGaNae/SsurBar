package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import com.ssurbar.survey.db.entity.survey.Template;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@ApiModel("RecentSurveyDetailRes")
public class RecentSurveyDetailRes extends BaseResponseBody {
    private String templateId;
    private String title;
    private String teamName;
    private String teamId;
    private String description;
}
