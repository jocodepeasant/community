package fzb.community.community.dto;

import fzb.community.community.model.User;
import lombok.Data;

/**
 * @author fzb
 */
@Data
public class QuestionDTO {
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
    private User user;
}
