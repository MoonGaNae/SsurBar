package com.ssurbar.survey.db.entity.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IntegratedFeedback{
    @Id
    private String surveyId;

    private String comment;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="survey_id", referencedColumnName = "survey_id")
    private Survey survey;
}
