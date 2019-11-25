package fzb.community.exception;

/**
 * @author fzb
 */
public interface ICustomizeErrorCode {

    /**
     * 获取自定义错误信息
     * @return
     */
    String getMessage();

    /**
     * 获取错误码信息
     * @return
     */
    Integer getCode();
}
