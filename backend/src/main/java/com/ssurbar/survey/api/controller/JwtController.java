package com.ssurbar.survey.api.controller;

import com.ssurbar.survey.common.jwt.AuthenticationToken;
import com.ssurbar.survey.common.jwt.AuthenticationTokenProvider;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import com.ssurbar.survey.db.entity.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "응답 API", tags = {"Response"})
@RestController
@RequestMapping("/api/v1/jwt")
public class JwtController {

    @Autowired
    AuthenticationTokenProvider authenticationTokenProvider;

    @PostMapping("/test")
    @ApiOperation(value = "응답 저장", notes = "설문에 대한 응답자의 답변을 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> saveAnswer(
    ){
        UserInfo user = UserInfo.builder().userId("ididididi").userType(UserRole.ADMIN).build();

        AuthenticationToken token = authenticationTokenProvider.issue(user);

        System.out.println(authenticationTokenProvider.validateToken(token.getToken()));
        System.out.println(token.getToken());
        UserInfo getUser = authenticationTokenProvider.getTokenUser(token.getToken());

        System.out.println(getUser.getUserId());
        System.out.println(getUser.getUserType().name());

        return ResponseEntity.status(200).body(BaseResponseBody.of("HI"));
    }
}
