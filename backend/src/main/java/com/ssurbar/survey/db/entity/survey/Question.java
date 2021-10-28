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
import lombok.Setter;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

    @Id
    private String questionId;

    private String title;
    private String questionType;
    private String content;
    private Boolean isEssential;
    private Integer questionNum;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
