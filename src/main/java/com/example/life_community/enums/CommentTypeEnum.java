package com.example.life_community.enums;

public enum CommentTypeEnum {
    QUESTION(1),// 回复问题
    COMMENT(2);// 回复评论

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        System.out.println("isExist - type：" + type);
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            System.out.println("for type：" + commentTypeEnum.getType());
            if(commentTypeEnum.getType() == type) return true;
        }
        return false;
    }
}
