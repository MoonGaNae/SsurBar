package com.ssurbar.survey.api.response;

import com.ssurbar.survey.common.model.response.BaseResponseBody;
import com.ssurbar.survey.db.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginPostRes extends BaseResponseBody {
    private String userId;
    private UserRole userType;
}
