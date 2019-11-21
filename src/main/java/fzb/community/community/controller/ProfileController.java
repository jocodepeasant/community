package fzb.community.community.controller;

import fzb.community.community.dto.PaginationDTO;
import fzb.community.community.dto.QuestionDTO;
import fzb.community.community.model.User;
import fzb.community.community.service.PaginationService;
import fzb.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

           /* PaginationDTO pagination = paginationService.Ownerpagination(user.getId());
            model.addAttribute("pagination", pagination);*/
        }
        else if (action.equals("replies")){

        }
        return "profile";
    }
}
