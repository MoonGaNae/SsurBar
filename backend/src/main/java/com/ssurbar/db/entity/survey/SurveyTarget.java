package com.ssurbar.db.entity.survey;

import com.ssurbar.db.entity.Team;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
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
