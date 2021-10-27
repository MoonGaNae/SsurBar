package com.ssurbar.survey.db.entity;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Builder
public class Team implements Serializable {

    @Id
    private String teamId;

    private String name;
}
