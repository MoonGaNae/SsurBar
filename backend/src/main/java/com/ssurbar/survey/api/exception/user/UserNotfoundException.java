package com.ssurbar.survey.api.exception.user;

import com.ssurbar.survey.common.exception.CustomException;
import com.ssurbar.survey.common.exception.ErrorCode;

public class UserNotfoundException extends CustomException {

    public UserNotfoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
