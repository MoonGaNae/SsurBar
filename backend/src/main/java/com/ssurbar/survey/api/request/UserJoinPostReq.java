package com.ssurbar.survey.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserJoinPostReq {

    private String name;
    private String email;
    private String password;
    private String employeeNumber;
}
