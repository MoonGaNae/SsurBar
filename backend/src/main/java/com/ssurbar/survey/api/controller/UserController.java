package com.ssurbar.survey.api.controller;

import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.request.UserJoinPutReq;
import com.ssurbar.survey.api.request.UserLoginPostReq;
import com.ssurbar.survey.api.response.UserDetail;
import com.ssurbar.survey.api.response.UserJoinPostRes;
import com.ssurbar.survey.api.response.UserLoginPostRes;
import com.ssurbar.survey.api.response.UserUncertifiedGetRes;
import com.ssurbar.survey.api.service.UserService;
import com.ssurbar.survey.common.jwt.AuthenticationToken;
import com.ssurbar.survey.common.jwt.AuthenticationTokenProvider;
import com.ssurbar.survey.common.model.common.UserInfo;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 사용자 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes="서비스에서 제공하는 회원가입, 관리자의 인증을 통해 정회원이 될 수 있음.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> signup(
            @RequestBody UserJoinPostReq userJoinPostReq
            ){

        String userId = userService.join(userJoinPostReq);

        return ResponseEntity.status(201).body(UserJoinPostRes.builder().userId(userId).build());
    }

    @PostMapping("/login")
    @ApiOperation(value="로그인", notes="서비스에서 제공하는 로그인, 이메일과 패스워드가 일치할 경우 로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> login(
            @RequestBody UserLoginPostReq userLoginPostReq
    ){
        // 로그인 확인
        UserInfo userInfo = userService.login(userLoginPostReq);

        // 로그인 성공실패 -> 401
        if(userInfo == null){
            return ResponseEntity.status(401).body(BaseResponseBody.of("로그인 실패"));
        }

        // 성공시 토큰생성해서 보냄
        AuthenticationToken authenticationToken = authenticationTokenProvider.issue(userInfo);
        HttpHeaders header = new HttpHeaders();
        header = authenticationTokenProvider.setTokenHeader(header, authenticationToken.getToken());


        return ResponseEntity.status(200).headers(header).body(UserLoginPostRes.builder()
                .userId(userInfo.getUserId())
                .userType(userInfo.getUserType())
                .build()
        );
    }

    @PutMapping("/certification")
    @ApiOperation(value="회원가입 승인", notes="서비스 관리자가 회원가입 요청한 회원의 상태를 변경함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> certified(
            HttpServletRequest request,
            @RequestBody UserJoinPutReq userJoinPutReq
    ){
        userService.certified(request, userJoinPutReq);

        return ResponseEntity.status(201).body(BaseResponseBody.of("요청은 성공했다 디비를 확인하라"));
    }

    @GetMapping("/uncertified")
    @ApiOperation(value="미인증 회원조회", notes="서비스 관리자가 미인증 회원을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> uncertifiedList(
            HttpServletRequest request
    ){
        UserUncertifiedGetRes res = userService.uncertifiedList(request);
        
        return ResponseEntity.status(200).body(res);
    }
}

