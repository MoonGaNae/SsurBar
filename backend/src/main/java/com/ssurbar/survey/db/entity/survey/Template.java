package com.ssurbar.survey.db.entity.survey;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Template implements Serializable {

    @Id
    private String templateId;

    private String title;

    private String description;

    @Column(columnDefinition = "boolean default false")
    private boolean isDefault;

    @OneToMany(mappedBy = "template")
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "template")
    private List<Question> questions = new ArrayList<>();
}
