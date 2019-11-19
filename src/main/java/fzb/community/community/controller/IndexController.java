package fzb.community.community.controller;

import fzb.community.community.mapper.UserMapper;
import fzb.community.community.model.User;
import fzb.community.community.service.UserService;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User user=new User();
        if (cookies!=null) {
            for (Cookie cookie : cookies
            ) {
                if (cookie.getName().equals("GithubToken") ) {
                    user = userService.findByToken(cookie.getValue());
                    break;
                }
            }
        }
        if (user.getToken()!=null){
            request.getSession().setAttribute("GithubUser",user);
        }
        return "index";
    }
}
