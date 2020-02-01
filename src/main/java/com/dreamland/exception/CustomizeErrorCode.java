package com.dreamland.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不存在！要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题进行回复！"),
    NO_LOGIN(2003, "当前未登录,请登录后再试！"),
    SYS_ERROR(2004, "服务器冒烟了，稍后再试试！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "你回复的评论不存在！要不要换个试试？"),
    COMMENT_IS_EMPTY(2007, "输入内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "不能偷看别人的信息！"),
    NOTIFICATION_NOT_FOUND(2009, "消息消失了！"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败！")
    ;
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
