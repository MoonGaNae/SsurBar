package com.ssurbar.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssurbar.api.request.SurveyCreatePostReq;
import com.ssurbar.api.service.SurveyService;
import com.ssurbar.common.model.response.BaseResponseBody;
import com.ssurbar.db.entity.survey.Survey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 설문지 내용 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "설문지 API", tags = {"Survey"})
@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @PostMapping()
    @ApiOperation(value = "설문 생성", notes = "새로운 설문지를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createNewSurvey(SurveyCreatePostReq surveyCreatePostReq){
    	
    	Survey survey = surveyService.createNewSurvey(surveyCreatePostReq);
    	
    	if(survey == null) {
    		return ResponseEntity.status(401).body(BaseResponseBody.of(401, "tlfvo"));
    	}
    	
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "성공"));
    }
    
    @GetMapping()
    @ApiOperation(value = "전체 설문 목록 불러오기", notes = "모든 설문 목록을 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAllSurveyList(){
    	
    	List<Survey> survey = surveyService.getAllSurveyList();
    	
    	if(survey == null) {
    		return ResponseEntity.status(401).body(BaseResponseBody.of(404, "없음"));
    	}
    	
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "성공"));
    }

}
