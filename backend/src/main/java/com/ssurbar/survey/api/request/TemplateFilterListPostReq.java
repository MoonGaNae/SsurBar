package com.ssurbar.survey.api.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateFilterListPostReq {
    private List<String> filterQuestionList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FilterDto{
        private String title;
        private String content;
        private Integer number;
    }
}
