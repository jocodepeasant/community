package fzb.community.controller;

import fzb.community.dto.AccessTokenDTO;
import fzb.community.dto.GithubUser;
import fzb.community.model.User;
import fzb.community.provider.GithubProvider;
import fzb.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author fzb
 */
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
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);

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

    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response) {
        request.getSession().removeAttribute("GithubUser");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies
            ) {
            if (cookie.getName().equals("GithubToken")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:/";
    }
}
