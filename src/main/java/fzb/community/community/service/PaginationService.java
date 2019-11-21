package fzb.community.community.service;

import fzb.community.community.dto.PaginationDTO;
import fzb.community.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO pagination(Integer page){
        PaginationDTO paginationDTO=new PaginationDTO();
        int total = questionMapper.total();
        total=total/10+1;
        paginationDTO.setPagination(total,page);
        return paginationDTO;
    }
}
