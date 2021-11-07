package com.ssurbar.survey.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionData {
    private int number;
    private String title;
    private double averageScore;
    private double standardDeviation;
}
