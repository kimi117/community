package com.example.life_community.exception;

public class CustomizeException extends RuntimeException {
// 自定义异常类

    private String code;
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    public CustomizeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
