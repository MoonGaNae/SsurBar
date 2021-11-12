package com.ssurbar.survey.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;
}
