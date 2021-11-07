package com.ssurbar.survey.common.model.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class QuestionAnswerInfo {
    private int number;
    private String title;
    private double totalScore;
    private List<Double> scoreList;
    private Map<String, Integer> countMap;
}
