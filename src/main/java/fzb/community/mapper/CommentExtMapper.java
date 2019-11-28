package fzb.community.mapper;

import fzb.community.model.Comment;

public interface CommentExtMapper {

    /**
     * 增加评论数
     * @param comment
     */
    void incCommentCount(Comment comment);
}
