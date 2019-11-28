package fzb.community.mapper;

import fzb.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    /**
     * 增加评论数
     * @param question
     */
    void incCommentCount(Question question);
}
