package com.ssurbar.survey.db.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    ADMIN, CERTIFIED, UNCERTIFIED;

    @JsonCreator
    public static UserRole form(String s){
        return UserRole.valueOf(s.toUpperCase());
    }
}
