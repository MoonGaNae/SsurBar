package com.ssurbar.db.entity.answer;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssurbar.db.entity.survey.Question;
import com.ssurbar.db.entity.survey.Survey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class QuestionAnswer implements Serializable {
    @Id
    private String questionAnswerId;
    private String response;

    @ManyToOne
    @JoinColumn(name = "filter_data_id")
    private FilterData filterData;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
