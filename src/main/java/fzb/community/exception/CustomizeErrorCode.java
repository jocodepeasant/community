package fzb.community.exception;

/**
 * @author fzb
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你要找的问题不存在了，要不换个试试?"),
    QUSETION_IS_DELETE(2002, "你要修改或查找的问题不见了，请重新访问!"),
    CONTENT_IS_EMPTY(2003, "输入内容不能为空"),
    NO_LOGIN(2004,"未登录，请登录后访问!"),
    TARGET_PARAM_NOT_FOUND(2005, "未选中任何问题或评论进行回复"),
    TYPE_PARAM_WRONG(2006, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2007, "回复的评论不存在了，请重试"),
    INVALID_INPUT(2008, "无效地址，请确认请求参数是否正确"),
    NOTIFICATION_NOT_FOUND(2007,"消息莫非是不翼而飞了？"),
    Permission_DENIED(2008,"您没有当前操作的权限"),
    ;


    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
