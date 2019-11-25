package fzb.community.controller;

import fzb.community.dto.QuestionDTO;
import fzb.community.model.User;
import fzb.community.service.PaginationService;
import fzb.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author fzb
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PaginationService paginationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "10") Integer size){
        User user = (User) request.getSession().getAttribute("GithubUser");
        if (user==null){
            return "redirect:/";
        }
        if (action.equals("questions")){
            List<QuestionDTO> questionsByCreator = questionService.findQuestionsByCreator(user.getId());
            model.addAttribute("questions", questionsByCreator);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if (action.equals("replies")) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName","我的回复");
        }
        return "profile";
    }
}
