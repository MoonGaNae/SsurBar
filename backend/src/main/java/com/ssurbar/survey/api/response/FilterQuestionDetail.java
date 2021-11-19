package com.ssurbar.survey.api.response;

import com.ssurbar.survey.db.entity.survey.FilterQuestion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilterQuestionDetail {
    private String filterQuestionId;
    private String title;
    private String content;
    private Integer questionNum;

    public static FilterQuestionDetail of(FilterQuestion filterQuestion){

        return FilterQuestionDetail.builder()
                .filterQuestionId(filterQuestion.getFilterQuestionId())
                .content(filterQuestion.getContent())
                .title(filterQuestion.getTitle())
                .questionNum(filterQuestion.getQuestionNum())
                .build();
    }
}
