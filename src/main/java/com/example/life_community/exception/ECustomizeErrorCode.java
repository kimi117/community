package com.example.life_community.exception;

// 为了后面不会类爆炸
public enum ECustomizeErrorCode implements ICustomizeErrorCode {
// 自定义枚举类

    QUESTION_NOT_FOUND(2001, "当前问题不存在，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "未登录不能进行评论， 请先登录"),
    SYS_ERROR(2004, "服务太热了，要不然稍等下再来试试"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不换个试试？"),
    COMTENT_IS_EMPTY(2007, "输入内容不能为空")
    ;

    private Integer code;
    private String message;

    ECustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
