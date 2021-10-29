package com.ssurbar.survey.db.entity.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FilterQuestion implements Serializable {

    @Id
    private String filterQuestionId;

    private String title;
    private String content;
    private Integer questionNum;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;
}
