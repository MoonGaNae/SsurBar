package com.ssurbar.survey.common.model.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionCreateResult {
    private List<String> questionIdList;
    private boolean isExist;
}
