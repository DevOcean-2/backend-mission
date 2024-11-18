package com.devocean.Balbalm.global.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CommonRestExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public <T> CommonResponse commonExceptionHandler(CommonException ex) {
        return CommonResponse.fail(ex.getCode());
    }
}