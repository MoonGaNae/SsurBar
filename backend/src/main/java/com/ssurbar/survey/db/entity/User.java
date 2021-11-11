package com.ssurbar.survey.db.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 유저 정보를 담을 User Entity
 */

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    private String name;
    private String password;
    private String email;

    private String employeeNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userType;
    private LocalDateTime joinDate;
}
