package com.ssurbar.survey.api.service;

import java.util.List;

import com.ssurbar.survey.api.response.TeamInfo;

public interface TeamService {
    // 팀 목록 조회
    List<TeamInfo> getTeamList();
}
