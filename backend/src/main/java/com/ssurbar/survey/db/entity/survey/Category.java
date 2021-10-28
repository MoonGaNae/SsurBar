package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category implements Serializable {

    @Id
    private String categoryId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "category")
    private List<Question> questions = new ArrayList<>();

}
