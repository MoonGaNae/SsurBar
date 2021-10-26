package com.ssurbar.db.entity.survey;

import com.ssurbar.db.entity.answer.QuestionAnswer;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
public class Survey implements Serializable {

    @Id
    private String surveyId;

    private Date creationTime;

    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

    @OneToMany(mappedBy = "servey")
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "servey")
    private List<SurveyTarget> surveyTargets = new ArrayList<>();

}