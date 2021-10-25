package com.ssurbar.db.entity.survey;

import com.ssurbar.db.entity.survey.Survey;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
public class SurveyForm implements Serializable {

    @Id
    private String surveyFormId;

    private String title;

    private String desc;

    @OneToMany(mappedBy = "surveyForm")
    private List<Survey> surveys = new ArrayList<>();
}
