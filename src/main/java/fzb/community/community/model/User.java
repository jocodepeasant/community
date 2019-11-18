package fzb.community.community.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class User {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
}
