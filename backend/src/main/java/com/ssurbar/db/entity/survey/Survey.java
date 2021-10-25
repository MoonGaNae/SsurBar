package com.ssurbar.db.entity.survey;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import java.util.Date;

@Entity
@Builder
public class Survey implements Serializable {

    @Id
    private String serveyId;

    private Date creationTime;

    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

}
