package fzb.community.community.controller;

import fzb.community.community.dto.AccessTokenDTO;
import fzb.community.community.dto.GithubUser;
import fzb.community.community.mapper.UserMapper;
import fzb.community.community.model.User;
import fzb.community.community.provider.GithubProvider;
import fzb.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

@Controller
public class GithubLoginController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${Github.client_id}")
    private String client_id;

    @Value("${Github.client_secret}")
    private String client_secret;

    @Value("${Github.redirect_uri}")
    private String redirect_uri;

    @Autowired
    private UserService userService;


    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state",required = false) String state,
                           HttpServletResponse response){
        //获取用户信息
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        //判断用户信息是否正确进行操作
        if (githubUser!=null && githubUser.getId()!=null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.InsertOrUpdate(user);
            Cookie cookie = new Cookie("GithubToken", user.getToken());
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}
