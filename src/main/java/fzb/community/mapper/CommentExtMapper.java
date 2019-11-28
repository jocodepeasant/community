package fzb.community.mapper;

import fzb.community.model.Comment;

public interface CommentExtMapper {

    void incCommentCount(Comment comment);
}
