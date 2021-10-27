package com.ssurbar.survey.db.entity.survey;

import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "survey")
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<SurveyTarget> surveyTargets = new ArrayList<>();

}
