package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TemplateQuestionListGetRes extends BaseResponseBody {
    private List<QuestionDetail> questionList;
}
