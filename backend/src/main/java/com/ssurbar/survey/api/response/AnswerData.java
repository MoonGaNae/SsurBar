package com.ssurbar.survey.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AnswerData {
    private String categoryName;
    private List<QuestionData> questionDataList;
    private double averageScore;
    private double standardDeviation;
}
