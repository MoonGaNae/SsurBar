package com.ssurbar.survey.db.entity.answer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class FilterData implements Serializable {
    @Id
    private String filterDataId;
    private String response;

    @OneToMany(mappedBy = "filterData")
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();
}
