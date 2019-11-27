package fzb.community.enums;

public enum CommentTypeEnum {
    FIRST_COMMENT(1),
    SECOND_COMMENT(2);
    private Integer type;
    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
