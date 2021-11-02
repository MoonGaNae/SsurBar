package com.ssurbar.survey.api.controller;


import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.api.response.*;
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

    @GetMapping("/{templateId}/questions")
    @ApiOperation(value = "서식 문항 조회", notes="서식에 속한 문항들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getQuestions(
            @PathVariable("templateId") String templateId
    ){
        List<QuestionDetail> questionList = templateService.getQuestions(templateId);

        if(questionList == null || questionList.size()==0){
            return ResponseEntity.status(404).body(BaseResponseBody.of("서식 없음"));
        }

        return ResponseEntity.status(200).body(TemplateQuestionListGetRes.builder().questionList(questionList).build());
    }

    @GetMapping("/default")
    @ApiOperation(value = "기본 서식 목록 조회", notes="서비스에서 기본적으로 제공하는 기본서식들을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "기본서식 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getDefaultTemplate(){

        List<TemplateInfo> templateList = templateService.getDefaultTemplate();

        if(templateList.size() == 0){
            return ResponseEntity.status(404).body(BaseResponseBody.of("기본서식 없음"));
        }

        return ResponseEntity.status(200).body(TemplateDefaultGetRes.builder().templateList(templateList).build());
    }
}
