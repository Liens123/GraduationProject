package org.example.graduation_project.exception;

import org.example.graduation_project.api.inner.resp.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获并统一返回
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BizException.class)
    public ApiResponse<?> handleBizException(BizException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // 处理其他异常
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        return ApiResponse.error("系统错误，请联系管理员");
    }
}
