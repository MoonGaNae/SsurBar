package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "template_id")
    private Template template;
}
