package com.ssurbar.survey.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssurbar.survey.api.response.TeamInfo;
import com.ssurbar.survey.api.response.TeamListRes;
import com.ssurbar.survey.api.service.TeamService;
import com.ssurbar.survey.common.model.response.BaseResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 팀 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@CrossOrigin(origins = "*")
@Api(value = "팀 API", tags = {"Team"})
@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
	
	@Autowired
	TeamService teamService;

    @GetMapping()
    @ApiOperation(value = "팀 목록", notes = "회사 내의 팀 전체 목록을 불러온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getTeamList(){
    	
    	List<TeamInfo> list = teamService.getTeamList();
    	
    	TeamListRes res = TeamListRes.builder().teamList(list).build();
    	res.setMessage("성공");
    	
        return ResponseEntity.status(200).body(res);
    }
}
