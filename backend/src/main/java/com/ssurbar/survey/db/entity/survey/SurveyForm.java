package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyForm implements Serializable {

    @Id
    private String surveyFormId;

    private String title;

    private String description;

    @OneToMany(mappedBy = "surveyForm")
    private List<Survey> surveys = new ArrayList<>();
}
