package com.ssurbar.survey.api.response;

import com.ssurbar.survey.db.entity.survey.Template;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TemplateInfo {
    private String templateId;
    private String title;
    private String description;

    public static TemplateInfo of(Template template){
        return TemplateInfo.builder()
                .templateId(template.getTemplateId())
                .title(template.getTitle())
                .description(template.getDescription())
                .build();
    }
}
