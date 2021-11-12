package com.ssurbar.survey.api.request;

import com.ssurbar.survey.db.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserJoinPutReq {
    private String userId;
    private UserRole userType;
}
