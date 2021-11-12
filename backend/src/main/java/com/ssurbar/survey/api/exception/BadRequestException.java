package com.ssurbar.survey.api.exception;

import com.ssurbar.survey.common.exception.CustomException;
import com.ssurbar.survey.common.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
