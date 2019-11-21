package fzb.community.community.service;

import fzb.community.community.dto.QuestionDTO;
import fzb.community.community.mapper.QuestionMapper;
import fzb.community.community.mapper.UserMapper;
import fzb.community.community.model.Question;
import fzb.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 暂时只是插入
     * @param question
     */
    public void createOrUpdate(Question question){
        questionMapper.Insert(question);
    }

    public List<QuestionDTO> findQuestionsByCreator(Long creator){
                List<QuestionDTO> questionDTOS=new ArrayList<>();
                List<Question> byCreator = questionMapper.findByCreator();
                User byId = userMapper.findById(creator);
                for (Question question:byCreator
                ) {
                    QuestionDTO questionDTO=new QuestionDTO();
                    BeanUtils.copyProperties(question,questionDTO);
                    questionDTO.setUser(byId);
            questionDTOS.add(questionDTO);
        }
                return questionDTOS;
    }
}
