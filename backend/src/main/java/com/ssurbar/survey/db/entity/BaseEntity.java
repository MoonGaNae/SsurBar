package com.ssurbar.survey.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * BaseEntity ( Entity 간 공통 컬럼 정의. ).
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    String id = null;
}
