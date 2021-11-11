package com.ssurbar.survey.common.model.common;

import com.ssurbar.survey.db.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfo {
    private String userId;
    private UserRole userType;
}
