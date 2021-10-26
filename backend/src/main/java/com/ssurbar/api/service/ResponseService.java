package com.ssurbar.api.service;

import com.ssurbar.api.request.ResponsePostReq;

public interface ResponseService {
    /* 설문지에 대한 응답자의 답변 저장 */
    void saveAnswer(ResponsePostReq responsePostReq);
}
