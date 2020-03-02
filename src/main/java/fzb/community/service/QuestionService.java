package fzb.community.service;

import fzb.community.dto.PaginationDTO;
import fzb.community.dto.QuestionDTO;
import fzb.community.dto.QuestionQueryDTO;
import fzb.community.enums.SortEnum;
import fzb.community.exception.CustomizeErrorCode;
import fzb.community.exception.CustomizeException;
import fzb.community.mapper.QuestionExtMapper;
import fzb.community.mapper.QuestionMapper;
import fzb.community.mapper.UserMapper;
import fzb.community.model.Question;
import fzb.community.model.QuestionExample;
import fzb.community.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

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

    public PaginationDTO list(String search, String tag, String sort, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replaceAll("\\*|\\+|\\?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum:SortEnum.values()){
            if(sortEnum.name().toLowerCase().equals(sort)){
                questionQueryDTO.setSort(sort);

                if(sortEnum==SortEnum.HOT7){
                    questionQueryDTO.setTime(System.currentTimeMillis()-1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum==SortEnum.HOT30){
                    questionQueryDTO.setTime(System.currentTimeMillis()-1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        Integer totalPage;
        Integer totalCount =questionExtMapper.countBySearch(questionQueryDTO);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage  && totalPage!=0) {
            page = totalPage;
        }
        log.info(String.valueOf(size*(page-1)));
        log.info(String.valueOf(size));
        log.info(String.valueOf(page));

        questionQueryDTO.setSize(size);
        questionQueryDTO.setPageOffSet(size*(page-1));
        List<Question> questions=questionExtMapper.selectBySearch(questionQueryDTO);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalPage, page);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            User byId = userMapper.selectByPrimaryKey(question.getCreator());
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(byId);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        int total = (int) questionMapper.countByExample(questionExample);
        total=total/10+1;
        paginationDTO.setPagination(total,page);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
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
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }
}
