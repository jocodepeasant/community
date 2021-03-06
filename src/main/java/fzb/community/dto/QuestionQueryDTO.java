package fzb.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Integer pageOffSet;
    private Integer size;
}
