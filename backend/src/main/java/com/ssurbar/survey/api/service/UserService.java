package com.ssurbar.survey.api.service;

import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.request.UserJoinPutReq;
import com.ssurbar.survey.api.request.UserLoginPostReq;
import com.ssurbar.survey.common.model.common.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public String join(UserJoinPostReq req);

    UserInfo login(UserLoginPostReq userLoginPostReq);

    void certified(HttpServletRequest request, UserJoinPutReq userJoinPutReq);
}
