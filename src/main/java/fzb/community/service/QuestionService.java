package fzb.community.service;

import fzb.community.dto.QuestionDTO;
import fzb.community.exception.CustomizeErrorCode;
import fzb.community.exception.CustomizeException;
import fzb.community.mapper.QuestionMapper;
import fzb.community.mapper.UserMapper;
import fzb.community.model.Question;
import fzb.community.model.User;
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
                List<Question> byCreator = questionMapper.findByCreator(creator);
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

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO=new QuestionDTO();
        Question question = questionMapper.selectById(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question,questionDTO);
        User byId = userMapper.findById(question.getCreator());
        questionDTO.setUser(byId);
        questionMapper.UpdateViewCutById(id);
        return questionDTO;
    }
}
