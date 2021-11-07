package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SurveyResultRes extends BaseResponseBody {
    private List<AnswerData> answerDataList;
    private List<QuestionData> lowestAverageList;
    private List<QuestionData> highestAverageList;
    private List<QuestionData> lowestStandardDeviationList;
    private List<QuestionData> highestStandardDeviationList;
}
