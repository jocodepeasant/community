package fzb.community.mapper;

import fzb.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    List<Question> selOrdMt(Long gmtModified);
}
