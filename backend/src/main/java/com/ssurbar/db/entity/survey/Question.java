package com.ssurbar.db.entity.survey;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
public class Question implements Serializable {

    @Id
    private String questionId;

    private String title;
    private String questionType;
    private String content;
    private Boolean isEssential;
    private Integer questionNum;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
