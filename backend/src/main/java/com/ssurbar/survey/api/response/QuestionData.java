package com.ssurbar.survey.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionData {
    private int number;
    private String title;
    private double averageScore;
    private double standardDeviation;
    private List<QuestionAnswerDto> questionAnswerDtoList;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnswerDto{
        private String sentence;
        private int count;
        private double percentage;
    }
}
