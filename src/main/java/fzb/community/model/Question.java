package fzb.community.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Question {
    private Long id;
    private String title;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private String description;
    public List<Integer> list=new LinkedList<>();
}
