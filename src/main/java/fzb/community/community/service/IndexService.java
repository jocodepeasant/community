package fzb.community.community.service;

import fzb.community.community.dto.PaginationDTO;
import fzb.community.community.mapper.QuestionMapper;
import fzb.community.community.mapper.UserMapper;
import fzb.community.community.model.Question;
import fzb.community.community.model.User;
import org.apache.ibatis.annotations.Select;
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


    public List<PaginationDTO> list(){
        List<PaginationDTO> paginationDTOList=new ArrayList<>();
        List<Question> all = questionMapper.findAll();
        for (Question question:all){
            PaginationDTO paginationDTO=new PaginationDTO();
            User byId = userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question,paginationDTO);
            paginationDTO.setUser(byId);
            paginationDTOList.add(paginationDTO);
        }
        return paginationDTOList;
    }
}
