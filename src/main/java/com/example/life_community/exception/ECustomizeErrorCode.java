package com.example.life_community.exception;

import org.omg.PortableInterceptor.SUCCESSFUL;

// 为了后面不会类爆炸
public enum ECustomizeErrorCode implements ICustomizeErrorCode {
// 自定义枚举类

    QUESTION_NOT_FOUND("当前问题不存在，要不换个试试？"),
    SUCCESS("200", "成功"),
    NOT_FOUND("404", "未找到资源"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!");

    private String code;
    private String message;

    ECustomizeErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ECustomizeErrorCode(String message) {

        this.message = message;
    }

    @Override
    public String getCode() {
        return null;
    }

    public String getMessage() {
        return message;
    }
}
