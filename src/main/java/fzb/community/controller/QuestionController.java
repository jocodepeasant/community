package fzb.community.controller;

import fzb.community.dto.GithubUser;
import fzb.community.dto.QuestionDTO;
import fzb.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fzb
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model,
                           HttpServletRequest request){
        QuestionDTO questionDTO=questionService.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}