package com.ssurbar.survey.db.entity.answer;

import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.entity.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="questionanswer")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="question_answer")
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
