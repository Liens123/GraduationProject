package org.example.graduation_project.exception;

/**
 * 自定义业务异常，用于在Service/Dao层抛出
 */
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}
