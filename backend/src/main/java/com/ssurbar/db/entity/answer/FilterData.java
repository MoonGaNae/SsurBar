package com.ssurbar.db.entity.answer;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Builder
public class FilterData implements Serializable {
    @Id
    private String filterDataId;
    private String response;
}
