package com.ssurbar.survey.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssurbar.survey.api.response.TeamInfo;
import com.ssurbar.survey.db.entity.Team;
import com.ssurbar.survey.db.repository.survey.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamRepository teamRepository;

	@Override
	public List<TeamInfo> getTeamList() {
		List<Team> teamList = teamRepository.findAll();
		
		List<TeamInfo> teamInfoList = new ArrayList<>();
		
		for (Team team : teamList) {
			teamInfoList.add(TeamInfo.builder()
					.teamId(team.getTeamId())
					.teamName(team.getName())
					.build());
		}
		
		return teamInfoList;
	}

}
