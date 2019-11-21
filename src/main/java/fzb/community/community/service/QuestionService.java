package fzb.community.community.service;

import fzb.community.community.dto.PaginationDTO;
import fzb.community.community.mapper.QuestionMapper;
import fzb.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    /**
     * 暂时只是插入
     * @param question
     */
    public void createOrUpdate(Question question){
        questionMapper.Insert(question);
    }
}
