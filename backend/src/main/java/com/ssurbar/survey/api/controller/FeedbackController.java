package com.ssurbar.survey.api.controller;

import com.ssurbar.survey.api.request.FeedBackPatchReq;
import com.ssurbar.survey.api.request.FeedBackPostReq;
import com.ssurbar.survey.api.response.FeedbackRes;
import com.ssurbar.survey.api.service.FeedbackService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;
import com.ssurbar.survey.db.entity.survey.IntegratedFeedback;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 통합 피드백 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "통합 피드백 API", tags = {"Feedback"})
@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping()
    @ApiOperation(value = "피드백 저장", notes = "설문 분석을 통한 피드백을 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> addFeedback(@RequestBody @ApiParam(value="통합 피드백", required = true) FeedBackPostReq feedBackPostReq){
        IntegratedFeedback feedback = feedbackService.addTotalFeedback(feedBackPostReq);
        FeedbackRes res = FeedbackRes.builder().comment(feedback.getComment()).build();
        res.setMessage("피드백이 정상적으로 저장되었습니다.");
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/{surveyId}")
    @ApiOperation(value = "피드백 불러오기", notes = "해당 설문지의 통합 피드백 내용을 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getFeedback(@PathVariable("surveyId") String surveyId){
        IntegratedFeedback feedback = feedbackService.getTotalFeedback(surveyId);
        FeedbackRes res = FeedbackRes.builder().comment(feedback.getComment()).build();
        res.setMessage("피드백을 정상적으로 불러왔습니다.");
        return ResponseEntity.status(200).body(res);
    }

    @DeleteMapping("/{surveyId}")
    @ApiOperation(value = "피드백 삭제", notes = "해당 피드백을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> deleteFeedback(@PathVariable("surveyId") String surveyId){
        feedbackService.deleteTotalFeedback(surveyId);
        return ResponseEntity.status(200).body(BaseResponseBody.of("피드백이 삭제되었습니다."));
    }

    @PutMapping("/{surveyId}")
    @ApiOperation(value = "피드백 편집", notes = "통합 피드백 내용을 편집한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> editFeedback(@PathVariable("surveyId") String surveyId, @RequestBody @ApiParam(value="통합 피드백", required = true) FeedBackPatchReq feedBackPatchReq){
        IntegratedFeedback newFeedback = feedbackService.editTotalFeedback(surveyId, feedBackPatchReq);
        FeedbackRes res = FeedbackRes.builder().comment(newFeedback.getComment()).build();
        res.setMessage("피드백이 정상적으로 수정되었습니다.");
        return ResponseEntity.status(200).body(res);
    }

}
