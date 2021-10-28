package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.ssurbar.survey.db.entity.answer.QuestionAnswer;

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
public class Survey implements Serializable {

    @Id
    private String surveyId;

    private Date creationTime;

    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "survey" , cascade = CascadeType.ALL)
    private List<SurveyTarget> surveyTargets = new ArrayList<>();

}
