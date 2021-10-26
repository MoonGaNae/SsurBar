package com.ssurbar.api.controller;

import com.ssurbar.api.request.ResponsePostReq;
import com.ssurbar.api.service.ResponseService;
import com.ssurbar.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 응답 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "응답 API", tags = {"Response"})
@RestController
@RequestMapping("/api/v1/response")
public class ResponseController {

    @Autowired
    ResponseService responseService;

    @PostMapping()
    @ApiOperation(value = "응답 저장", notes = "설문에 대한 응답자의 답변을 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> saveAnswer(
            @RequestBody @ApiParam(value="사용자 응답", required = true) ResponsePostReq responsePostReq){
        responseService.saveAnswer(responsePostReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of("사용자 응답이 정상적으로 저장되었습니다."));
    }
}