package com.ssurbar.survey.config;

import com.ssurbar.survey.common.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns(
                        "/api/v1/template/**/questions",
                        "/api/v1/template/**/info",
                        "/api/v1/response",
                        "/api/v1/survey/**/answer",
                        "/api/v1/survey/**/info",
                        "/api/v1/survey/**/filters",
                        "/api/v1/survey/decode-link",
                        "/api/v1/users/join",
                        "/api/v1/users/login"
                )
        ;
    }
}
/*
적용
피드백, 팀정보, 설문생성 POST, 서식생성 POST,

제외 :
        서식정보불러오기
설문정보가져오기
질문가져오기, 필터질문가져오기
응답 생성
        설문 응답 목록 불러오기 -> /{survey_id}/answer
*/