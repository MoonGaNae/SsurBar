package com.ssurbar.survey.api.response;

import com.ssurbar.survey.db.entity.Code;
import com.ssurbar.survey.db.entity.survey.Category;
import com.ssurbar.survey.db.entity.survey.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionDetail {

    private String questionId;
    private String title;
    private String questionType;
    private String content;
    private Boolean isEssential;
    private Integer questionNum;

    private String categoryId;
    private String categoryName;

    public static QuestionDetail of(Question question){
        Category category = question.getCategory();
        Code questionType = question.getQuestionType();
        return QuestionDetail.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .questionType(questionType.getCode())
                .isEssential(question.getIsEssential())
                .questionNum(question.getQuestionNum())
                .categoryId(category.getCategoryId())
                .categoryName(category.getName())
                .build();
    }
}
