package fzb.community.service;

import fzb.community.dto.PaginationDTO;
import fzb.community.mapper.QuestionMapper;
import fzb.community.model.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO pagination(Integer page){
        PaginationDTO paginationDTO=new PaginationDTO();
        int total = (int) questionMapper.countByExample(new QuestionExample());
        total=total/10+1;
        paginationDTO.setPagination(total,page);
        return paginationDTO;
    }

   /* public PaginationDTO Ownerpagination(Long creator) {
        PaginationDTO paginationDTO=new PaginationDTO();
        int total = questionMapper.Ownertotal(creator);
        total=total/10+1;
        paginationDTO.setPagination(total,page);
        return paginationDTO;
    }*/
}
