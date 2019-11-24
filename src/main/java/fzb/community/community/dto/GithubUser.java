package fzb.community.community.dto;

import lombok.Data;

/**
 * @author fzb
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
