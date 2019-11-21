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
public class IndexService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> list(Integer page,Integer size){
        List<QuestionDTO> questionDTOList=new ArrayList<>();
            List<Question> all = questionMapper.findPageAll((page-1)*size,size);
            for (Question question:all){
                QuestionDTO questionDTO=new QuestionDTO();
                User byId = userMapper.findById(question.getCreator());
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(byId);
                questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

}
