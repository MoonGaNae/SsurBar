package com.ssurbar.survey.api.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class TemplateQuestionListPostReq {
    private List<String> categoryList;
    private List<String> questionList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class QuestionDto{
        private String title;
        private String questionType;
        private String content;
        private Boolean isEssential;
        private Integer number;
        private String categoryName;
    }
}
