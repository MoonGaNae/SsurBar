package com.ssurbar.api.controller;

import com.ssurbar.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 설문지 내용 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "설문지 API", tags = {"Survey"})
@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {

    @PostMapping()
    @ApiOperation(value = "설문 생성", notes = "새로운 설문지를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createNewSurvey(){
        return null;
    }

}
