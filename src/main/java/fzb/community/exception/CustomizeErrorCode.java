package fzb.community.exception;

/**
 * @author fzb
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001, "你要找的问题不存在了，要不换个试试?"),
    QUSETION_IS_DELETE(2002,"你要修改或查找的问题不见了，请重新访问!"),
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
