package com.ssurbar.survey.api.controller;

import java.util.List;

import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.response.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssurbar.survey.api.request.SurveyAnswerListGetReq;
import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.service.AnswerService;
import com.ssurbar.survey.api.service.SurveyService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;

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
    
    @Autowired
    AnswerService answerService;

    @PostMapping()
    @ApiOperation(value = "설문 생성", notes = "새로운 설문지를 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> createNewSurvey(@RequestBody SurveyCreatePostReq surveyCreatePostReq){
    	
    	boolean isSuccess = surveyService.createNewSurvey(surveyCreatePostReq);
    	
    	if(!isSuccess) {
    		return ResponseEntity.status(401).body(BaseResponseBody.of("실패"));
    	}
    	
        return ResponseEntity.status(200).body(BaseResponseBody.of("성공"));
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
    	List<SurveyInfo> surveyList = surveyService.getAllSurveyList();
    	
    	if(surveyList == null) {
    		return ResponseEntity.status(401).body(BaseResponseBody.of("없음"));
    	}
    	
    	SurveyListRes res = SurveyListRes.builder().surveyList(surveyList).build();
    	res.setMessage("성공");
    	
        return ResponseEntity.status(200).body(res);
    }
    
//    @GetMapping()
//    @ApiOperation(value = "나의 설문 목록 불러오기", notes = "내가 생성한 설문 목록을 불러온다.")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity<? extends BaseResponseBody> getMySurveyList(@ApiIgnore @RequestHeader("Authorization") String accessToken){
//    	
//    	List<Survey> survey = surveyService.getMySurveyList(accessToken);
//    	
//    	if(survey == null) {
//    		return ResponseEntity.status(401).body(BaseResponseBody.of(404, "없음"));
//    	}
//    	
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "성공"));
//    }
    
	  @GetMapping("/{surveyId}/response/cnt")
	  @ApiOperation(value = "설문 응답 수 불러오기", notes = "해당 설문에 응답한 횟수를 불러온다.")
	  @ApiResponses({
	          @ApiResponse(code = 200, message = "성공"),
	          @ApiResponse(code = 401, message = "인증 실패"),
	          @ApiResponse(code = 404, message = "사용자 없음"),
	          @ApiResponse(code = 500, message = "서버 오류")
	  })
	  public ResponseEntity<? extends BaseResponseBody> getSurveyReponseCount(@PathVariable String surveyId){
		  int count = surveyService.getSurveyResponseCount(surveyId);
		  
		  SurveyCountRes res = SurveyCountRes.builder().count(count).build();
		  res.setMessage("성공");
		  
	      return ResponseEntity.status(200).body(res);
	  }
	  
	  @GetMapping("/{surveyId}/answer")
	  @ApiOperation(value = "설문 응답 목록 불러오기", notes = "선택한 설문에 응답한 목록을 불러온다.")
	  @ApiResponses({
	          @ApiResponse(code = 200, message = "성공"),
	          @ApiResponse(code = 401, message = "인증 실패"),
	          @ApiResponse(code = 404, message = "사용자 없음"),
	          @ApiResponse(code = 500, message = "서버 오류")
	  })
	  public ResponseEntity<? extends BaseResponseBody> getSurveyAnswerList(@PathVariable String surveyId
			  , @ModelAttribute SurveyAnswerListGetReq surveyAnswerListGetReq)
	  {
		  List<SurveyAnswer> surveyAnswerList = answerService.getSurveyAnswerList(surveyId, surveyAnswerListGetReq);
		  
		  SurveyAnswerRes res = SurveyAnswerRes.builder().surveyAnswerList(surveyAnswerList).build();
		  res.setMessage("성공");
		  
	      return ResponseEntity.status(200).body(res);
	  }
	  
	  @GetMapping("/{surveyId}")
	  @ApiOperation(value = "설문 상제 정보 불러오기", notes = "선택한 설문에 대한 상세정보를 불러온다.")
	  @ApiResponses({
	          @ApiResponse(code = 200, message = "성공"),
	          @ApiResponse(code = 401, message = "인증 실패"),
	          @ApiResponse(code = 404, message = "사용자 없음"),
	          @ApiResponse(code = 500, message = "서버 오류")
	  })
	  public ResponseEntity<? extends BaseResponseBody> getSurveyDetail(@PathVariable String surveyId)
	  {
		  SurveyDetailRes res = surveyService.getSurveyDetailInfo(surveyId);
		  res.setMessage("성공");
		  
	      return ResponseEntity.status(200).body(res);
	  }

	@PostMapping("/{surveyId}/filters")
	@ApiOperation(value = "설문 필터 생성", notes = "새로운 설문의 필터를 생성해서 저장한다")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "설문 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> createFilters(
			@PathVariable("surveyId") String surveyId,
			@RequestBody @ApiParam(value="설문 필터생성", required = true) SurveyFilterListPostReq surveyFilterListPostReq
	){
		List<String> idList = surveyService.createNewFilters(surveyId, surveyFilterListPostReq);

		if(idList.size() == 0){
			return ResponseEntity.status(500).body(BaseResponseBody.of("서버오류"));
		}

		SurveyFilterListPostRes res = SurveyFilterListPostRes.builder().filterQuestionList(idList).build();
		return ResponseEntity.status(201).body(res);
	}

}
