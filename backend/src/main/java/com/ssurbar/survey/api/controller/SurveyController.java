package com.ssurbar.survey.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssurbar.survey.api.request.SurveyCreatePostReq;
import com.ssurbar.survey.api.request.SurveyDecodeLinkGetReq;
import com.ssurbar.survey.api.request.SurveyFilterListPostReq;
import com.ssurbar.survey.api.response.*;
import com.ssurbar.survey.api.service.AnswerService;
import com.ssurbar.survey.api.service.SurveyService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
    	
    	String surveyId = surveyService.createNewSurvey(surveyCreatePostReq);
    	
    	if(surveyId == null) {
    		return ResponseEntity.status(401).body(BaseResponseBody.of("실패"));
    	}

		SurveyCreatePostRes surveyCreatePostRes = SurveyCreatePostRes.builder().surveyId(surveyId).build();
		surveyCreatePostRes.setMessage("성공");

		return ResponseEntity.status(200).body(surveyCreatePostRes);
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

	@GetMapping("/recent")
	@ApiOperation(value = "최근 서식 목록 불러오기", notes = "최근 서식 목록을 불러온다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> getRecentList(){
		List<RecentSurveyInfo> surveyList = surveyService.getRecentSurveyList();

		if(surveyList == null) {
			return ResponseEntity.status(401).body(BaseResponseBody.of("없음"));
		}

		RecentSurveyListRes res = RecentSurveyListRes.builder().surveyList(surveyList).build();
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
	  
	  @GetMapping(value="/{surveyId}/answer")
	  @ApiOperation(value = "설문 응답 목록 불러오기", notes = "선택한 설문에 응답한 목록을 불러온다.")
	  @ApiResponses({
	          @ApiResponse(code = 200, message = "성공"),
	          @ApiResponse(code = 401, message = "인증 실패"),
	          @ApiResponse(code = 404, message = "사용자 없음"),
	          @ApiResponse(code = 500, message = "서버 오류")
	  })
	  public ResponseEntity<? extends BaseResponseBody> getSurveyAnswerList(@PathVariable String surveyId
			  , /*@ModelAttribute("filterDataList") SurveyAnswerListGetReq surveyAnswerListGetReq*/
	  @RequestParam String filterDataStr) throws UnsupportedEncodingException, JsonProcessingException {

		  SurveyResultRes res = answerService.getSurveyAnswerList(surveyId, filterDataStr);

//		  SurveyResultRes res = SurveyResultRes.builder().answerDataList(answerDataList).build();
		  res.setMessage("성공");

		  return ResponseEntity.status(200).body(res);
	  }
	  
	  @GetMapping("/{surveyId}/info")
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
		  
		  if(res == null){
			  return ResponseEntity.status(500).body(BaseResponseBody.of("실패"));
		  }
		  res.setMessage("성공");
		  
	      return ResponseEntity.status(200).body(res);
	  }

	@GetMapping("/recent/{surveyId}")
	@ApiOperation(value = "최근 설문 상제 정보 불러오기", notes = "선택한 최근 설문에 대한 상세정보를 불러온다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> getRecentSurveyDetail(@PathVariable String surveyId)
	{
		RecentSurveyDetailRes res  = surveyService.getRecentSurveyDetailInfo(surveyId);

		if(res == null){
			return ResponseEntity.status(500).body(BaseResponseBody.of("실패"));
		}
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

	@GetMapping("/{surveyId}/filters")
	@ApiOperation(value = "설문 필터 조회", notes = "해당 설문의 필터항목들을 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "설문 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> getFilters(
			@PathVariable("surveyId") String surveyId
	){
		List<FilterQuestionDetail> filterQuestionDetails = surveyService.getFilters(surveyId);

		if(filterQuestionDetails == null) return ResponseEntity.status(404).body(BaseResponseBody.of("설문 없음"));

		return ResponseEntity.status(200).body(SurveyFilterListGetRes.builder().filterQuestionList(filterQuestionDetails).build());
	}

	@GetMapping("/decode-link")
	@ApiOperation(value = "링크코드에 해당하는 설문, 서식 조회", notes = "해당 링크에 해당하는 설문, 서식의 Id를 조회한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "설문 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> decodeLink(
			@ApiParam(value="링크 조회", required = true)SurveyDecodeLinkGetReq surveyDecodeLinkGetReq
	){

		SurveyDecodeLinkGetRes res = surveyService.decodeLink(surveyDecodeLinkGetReq);

		if(res == null) return ResponseEntity.status(404).body(BaseResponseBody.of("설문 없음"));

		return ResponseEntity.status(200).body(res);
	}

	@GetMapping("/{surveyId}/template")
	@ApiOperation(value = "해당 설문과 동일한 템플릿을 사용한 설문들 조회", notes = "해당 설문과 동일한 템플릿을 사용한 설문들의 Id, 대상, 제목을 조회한다")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "설문 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> getSameTemplateSurvey(
			@PathVariable("surveyId")String surveyId
	){

		SameTemplateSurveyRes res = surveyService.getSameTemplateSurvey(surveyId);

		if(res == null) return ResponseEntity.status(404).body(BaseResponseBody.of("설문 없음"));

		return ResponseEntity.status(200).body(res);
	}

}
