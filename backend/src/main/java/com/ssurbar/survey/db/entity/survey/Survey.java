package com.ssurbar.survey.db.entity.survey;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.ssurbar.survey.db.entity.Team;
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
    
    private String responseUrl;
    
    private String resultUrl;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<FilterQuestion> filterQuestions = new ArrayList<>();
}
