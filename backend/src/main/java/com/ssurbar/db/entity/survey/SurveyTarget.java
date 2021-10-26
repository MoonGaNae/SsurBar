package com.ssurbar.db.entity.survey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.ssurbar.db.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyTarget implements Serializable {
    @Id
    private String surveyTargetId;
    private String responseUrl;
    private String resultUrl;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;
}
