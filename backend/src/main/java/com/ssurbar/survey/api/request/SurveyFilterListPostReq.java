package com.ssurbar.survey.api.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyFilterListPostReq {
    private List<String> filterQuestionList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FilterDto{
        private String content;
        private Integer number;
        private String title;
    }
}
