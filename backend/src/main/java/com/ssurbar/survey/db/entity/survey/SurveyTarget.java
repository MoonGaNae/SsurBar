package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ssurbar.survey.db.entity.Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
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
}
