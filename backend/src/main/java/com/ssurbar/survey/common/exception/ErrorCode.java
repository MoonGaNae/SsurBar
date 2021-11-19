package com.ssurbar.survey.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(400, "G001", "Bad Request"),
    USER_NOT_FOUND(404, "U001", "User Not Found"),
    USER_UNAUTHORIZED(401, "U002", "User unauthorized");


    private final int status;
    private final String code;
    private final String message;

}
