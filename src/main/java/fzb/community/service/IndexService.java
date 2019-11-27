package fzb.community.service;

import fzb.community.dto.QuestionDTO;
import fzb.community.mapper.QuestionExtMapper;
import fzb.community.mapper.QuestionMapper;
import fzb.community.mapper.UserMapper;
import fzb.community.model.Question;
import fzb.community.model.QuestionExample;
import fzb.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzb
 */
@Service
public class IndexService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> list(Integer page,Integer size){
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        QuestionExample questionExample=new QuestionExample();
        questionExample.setOrderByClause("gmt_modified DESC");
            List<Question> all = questionMapper.selectByExampleWithRowbounds
                    (questionExample,new RowBounds((page-1)*size,size));
            for (Question question:all){
                QuestionDTO questionDTO=new QuestionDTO();
                User byId = userMapper.selectByPrimaryKey(question.getCreator());
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(byId);
                questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

}
