package org.example.graduation_project.exception;

import org.example.graduation_project.api.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获并统一返回
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException ex) {
        return Result.error("A00003",ex.getMessage());
    }
}
