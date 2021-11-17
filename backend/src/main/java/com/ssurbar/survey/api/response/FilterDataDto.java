package com.ssurbar.survey.api.response;

import com.ssurbar.survey.db.entity.answer.QuestionAnswer;
import com.ssurbar.survey.db.entity.survey.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FilterDataDto {

    private String response;
    List<QuestionAnswerDto> questionAnswers;

    @Getter
    @Setter
    @Builder
    public static class QuestionAnswerDto {
        private String response;
        private String questionId;
        private int number;

        public static QuestionAnswerDto of(QuestionAnswer questionAnswer){

            Question question = questionAnswer.getQuestion();
            return QuestionAnswerDto.builder()
                    .questionId(question.getQuestionId())
                    .response(questionAnswer.getResponse())
                    .number(question.getQuestionNum())
                    .build();
        }

    }

}
