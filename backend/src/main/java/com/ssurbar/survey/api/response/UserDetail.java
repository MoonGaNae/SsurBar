package com.ssurbar.survey.api.response;

import com.ssurbar.survey.db.entity.User;
import com.ssurbar.survey.db.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDetail {
    private String userId;
    private String email;
    private String employeeNumber;
    private String name;
    private UserRole userType;

    public static UserDetail of(User user){

        return UserDetail.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .employeeNumber(user.getEmployeeNumber())
                .name(user.getName())
                .userType(user.getUserType())
                .build();
    }
}
