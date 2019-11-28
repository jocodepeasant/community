package fzb.community.service;

import fzb.community.dto.PaginationDTO;
import fzb.community.dto.QuestionDTO;
import fzb.community.exception.CustomizeErrorCode;
import fzb.community.exception.CustomizeException;
import fzb.community.mapper.QuestionMapper;
import fzb.community.mapper.UserMapper;
import fzb.community.model.Question;
import fzb.community.model.QuestionExample;
import fzb.community.model.User;
import org.apache.commons.lang3.StringUtils;
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

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        } else {
            Question question1 = questionMapper.selectByPrimaryKey(question.getId());
            if (question1 == null) {
                throw new CustomizeException(CustomizeErrorCode.QUSETION_IS_DELETE);
            } else {
                question.setGmtModified(System.currentTimeMillis());
                questionMapper.updateByPrimaryKeySelective(question);
            }
        }
    }

    public List<QuestionDTO> findQuestionsByCreator(Long creator) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(creator);
        List<Question> byCreator = questionMapper.selectByExample(questionExample);
        User byId = userMapper.selectByPrimaryKey(creator);
        for (Question question : byCreator
        ) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(byId);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        User byId = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(byId);
        Question updateQuestion=new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(question.getViewCount()+1);
        questionMapper.updateByPrimaryKeySelective(updateQuestion);
        return questionDTO;
    }

}
