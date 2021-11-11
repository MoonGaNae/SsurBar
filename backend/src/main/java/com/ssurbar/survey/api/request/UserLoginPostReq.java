package com.ssurbar.survey.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginPostReq {
    private String email;
    private String password;
}
