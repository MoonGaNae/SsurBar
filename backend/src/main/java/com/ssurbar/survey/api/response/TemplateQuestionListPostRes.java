package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class TemplateQuestionListPostRes extends BaseResponseBody {
    private List<String> questionList;
}
