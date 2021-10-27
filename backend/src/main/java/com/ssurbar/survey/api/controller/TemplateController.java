package com.ssurbar.survey.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssurbar.survey.common.model.response.BaseResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 설문지 서식 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "설문 서식 API", tags = {"Template"})
@RestController
@RequestMapping("/api/v1/template")
public class TemplateController {

    @PostMapping()
    @ApiOperation(value = "서식 생성", notes = "새로운 설문 서식지를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createNewTemplate(){
        return null;
    }


    @PostMapping("/{templateId}/questions")
    @ApiOperation(value = "서식 문항 생성", notes = "새로운 서식의 문항을 생성해서 저장한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createQuestions(@PathVariable("templateId") String templateId){
        return null;
    }

    @PostMapping("/{templateId}/filters")
    @ApiOperation(value = "서식 필터 생성", notes = "새로운 서식의 필터를 생성해서 저장한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createFilters(@PathVariable("templateId") String templateId){
        return null;
    }

}
