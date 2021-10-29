package com.ssurbar.survey.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    @Id
    private String code;
    private String name;

}
