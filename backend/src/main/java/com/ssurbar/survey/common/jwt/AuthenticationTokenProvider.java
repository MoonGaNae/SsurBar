package com.ssurbar.survey.common.jwt;

import com.ssurbar.survey.common.model.common.UserInfo;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationTokenProvider {

    /**
     * 헤더에 토큰삽입
     * @param header
     * @param token
     * @return
     */
    HttpHeaders setTokenHeader(HttpHeaders header, String token);

    /**
     * HTTP 요청에서 토큰 취득
     * @param request HTTP 요청
     * @return
     */
    String parseTokenString(HttpServletRequest request);

    /**
     * 토큰발급
     * @param userId
     * @return 토큰
     */
    AuthenticationToken issue(UserInfo user);

    /**
     * 토큰에서 userId 가져오기
     * @param token
     * @return userId
     */
    UserInfo getTokenUser(String token);

    /**
     * 토큰 유효성 검사
     * @param token
     * @return 유효성 여부
     */
    boolean validateToken(String token);
}
