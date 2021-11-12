package com.ssurbar.survey.api.exception.user;

import com.ssurbar.survey.common.exception.CustomException;
import com.ssurbar.survey.common.exception.ErrorCode;

public class UserUnauthorizedException extends CustomException {

    public UserUnauthorizedException(){
        super(ErrorCode.USER_UNAUTHORIZED);
    }
}
