package com.ssurbar.survey.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 유저 정보를 담을 User Entity
 */

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {


    private String password;
    private String email;

    @Column(name="employee_number", insertable=false, updatable=false)
    private String employeeNumber;

    @Column(name="user_type")
    private String userType;

    @Column(name="employee_number")
    private LocalDateTime joinDate;

    @Builder
    public User(String password, String email, String employeeNumber, String userType){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 13;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        this.id = generatedString;
        this.password = password;
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.userType = userType;
        this.joinDate = LocalDateTime.now();
    }

    //사용자의 가입을 승인했을 때 메소드

}
