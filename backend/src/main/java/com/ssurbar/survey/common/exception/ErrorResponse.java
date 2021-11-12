package com.ssurbar.survey.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String code;

    private int status;
}
