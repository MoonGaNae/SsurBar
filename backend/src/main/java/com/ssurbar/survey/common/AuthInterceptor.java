package com.ssurbar.survey.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssurbar.survey.common.jwt.AuthenticationTokenProvider;
import com.ssurbar.survey.common.model.common.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {


        String token = authenticationTokenProvider.parseTokenString(request);


        if(token != null && authenticationTokenProvider.validateToken(token)){
            UserInfo userInfo = authenticationTokenProvider.getTokenUser(token);
//            System.out.println("유저발견" + userInfo.getUserId());
            request.setAttribute("userId", userInfo.getUserId());
//            JsonObject jsonObject = new JsonObject();
//
//
//            jsonObject.addProperty("msg", "잘못된 토큰입니다.");
//
//            Gson gson = new Gson();
//            response.getWriter().write(gson.toJson(jsonObject));
//            response.setStatus(400);
//            return false;
        }

        response.setStatus(200);
        return true;
    }
}
