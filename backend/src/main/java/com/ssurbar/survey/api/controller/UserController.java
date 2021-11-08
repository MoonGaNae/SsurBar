package com.ssurbar.survey.api.controller;

import com.ssurbar.survey.api.request.UserJoinPostReq;
import com.ssurbar.survey.api.response.UserJoinPostRes;
import com.ssurbar.survey.api.service.UserService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

