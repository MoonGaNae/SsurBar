package com.ssurbar.survey.api.controller;


import com.ssurbar.survey.api.request.TemplateFilterListPostReq;
import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.api.response.TemplateFilterListPostRes;
import com.ssurbar.survey.api.response.TemplatePostRes;
import com.ssurbar.survey.api.response.TemplateQuestionListPostRes;
import com.ssurbar.survey.api.service.TemplateService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 설문지 서식 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "설문 서식 API", tags = {"Template"})
@RestController
@RequestMapping("/api/v1/template")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @PostMapping()
    @ApiOperation(value = "서식 생성", notes = "새로운 설문 서식지를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createNewTemplate(@RequestBody @ApiParam(value="설문 서식생성", required = true) TemplatePostReq templatePostReq){

        String templateId = null;
        try{
            templateId = templateService.createNewTemplate(templatePostReq);
        } catch (Exception e){
            return ResponseEntity.status(501).body(BaseResponseBody.of(e.getMessage()));
        }

        if(templateId == null){
            return ResponseEntity.status(500).body(BaseResponseBody.of("서버오류"));
        }

        TemplatePostRes res = TemplatePostRes.builder().templateId(templateId).build();
        res.setMessage("성공");
        return ResponseEntity.status(201).body(res);
    }


    @PostMapping("/{templateId}/questions")
    @ApiOperation(value = "서식 문항 생성", notes = "새로운 서식의 문항을 생성해서 저장한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createQuestions(
            @PathVariable("templateId") String templateId,
            @RequestBody @ApiParam(value="설문 서식 문항생성", required = true) TemplateQuestionListPostReq templateQuestionListPostReq
            ){

        List<String> idList = templateService.createNewQuestions(templateId, templateQuestionListPostReq);

        if(idList == null){
            return ResponseEntity.status(500).body(BaseResponseBody.of("서버오류"));
        }

        TemplateQuestionListPostRes res = TemplateQuestionListPostRes.builder().questionList(idList).build();
        return ResponseEntity.status(201).body(res);
    }

    @PostMapping("/{templateId}/filters")
    @ApiOperation(value = "서식 필터 생성", notes = "새로운 서식의 필터를 생성해서 저장한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createFilters(
            @PathVariable("templateId") String templateId,
            @RequestBody @ApiParam(value="설문 서식 필터생성", required = true)TemplateFilterListPostReq templateFilterListPostReq
            ){
        List<String> idList = templateService.createNewFilters(templateId, templateFilterListPostReq);

        if(idList.size() == 0){
            return ResponseEntity.status(500).body(BaseResponseBody.of("서버오류"));
        }

        TemplateFilterListPostRes res = TemplateFilterListPostRes.builder().filterQuestionList(idList).build();
        return ResponseEntity.status(201).body(res);
    }

}