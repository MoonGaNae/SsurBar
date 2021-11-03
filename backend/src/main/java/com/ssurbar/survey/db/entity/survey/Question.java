package com.ssurbar.survey.db.entity.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.ssurbar.survey.db.entity.Code;
import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
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
//    private String questionType;
    private String content;
    
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isEssential;
    private Integer questionNum;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "question_type")
    private Code questionType;
}
