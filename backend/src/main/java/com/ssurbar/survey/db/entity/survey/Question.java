package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

    @Id
    private String questionId;

    private String title;
    private String questionType;
    private String content;
    
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isEssential;
    private Integer questionNum;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
