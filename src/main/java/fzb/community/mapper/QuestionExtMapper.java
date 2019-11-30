package fzb.community.mapper;

import fzb.community.dto.QuestionQueryDTO;
import fzb.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    /**
     * 增加评论数
     * @param question
     */
    void incCommentCount(Question question);

    /**
     * 通过搜索条件取出总记录数
     * @param questionQueryDTO
     * @return
     */
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    /**
     * 通过搜索条件取出当前页的记录
     * @param questionQueryDTO
     * @return
     */
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

}
