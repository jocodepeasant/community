package fzb.community.community.dto;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
