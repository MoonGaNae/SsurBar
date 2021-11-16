package com.ssurbar.survey.db.entity.survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category implements Serializable {

    @Id
    private String categoryId;
    private String name;

    private int number;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "category")
    private List<Question> questions = new ArrayList<>();

}
