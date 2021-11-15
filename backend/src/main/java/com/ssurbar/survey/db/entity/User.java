package com.ssurbar.survey.db.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    public void changeUserType(UserRole userType){
        this.userType = userType;
    }
}
