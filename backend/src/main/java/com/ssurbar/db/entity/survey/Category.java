package com.ssurbar.db.entity.survey;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    private String categoryId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    private SurveyForm surveyForm;

    @OneToMany(mappedBy = "category")
    private List<Question> questions = new ArrayList<>();

}
