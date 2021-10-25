package com.ssurbar.db.entity.answer;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
public class FilterData implements Serializable {
    @Id
    private String filterDataId;
    private String response;

    @OneToMany(mappedBy = "filterData")
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();
}
