package com.ssurbar.survey.common.exception.handler;

import com.ssurbar.survey.common.exception.CustomException;
import com.ssurbar.survey.common.exception.ErrorCode;
import com.ssurbar.survey.common.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/*
 *
 * SPA처리를 위한 ControllerAdvice.
 * 요청에 해당하는 Request Mapping이 존재하지 않을 경우 Default로 index.html을 렌더링한다.
 *
 */

@ControllerAdvice
public class NotFoundHandler {
    @Value("${spa.default-file}")
    String defaultFile;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> renderDefaultPage(NoHandlerFoundException ex) {
        String url = ex.getRequestURL();
        if(url.startsWith("/api/")) {
            return ResponseEntity.notFound().build();
        }else {
            try {
                ClassPathResource classPathResource = new ClassPathResource(defaultFile);
                InputStream inputStream = classPathResource.getInputStream();
                String body = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
                return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(body);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error completing the action.");
            }
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
