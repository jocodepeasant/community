package fzb.community.enums;

/**
 * @author fzb
 */

public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status){
        this.status=status;
    }
}
