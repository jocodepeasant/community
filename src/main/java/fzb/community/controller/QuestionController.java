package fzb.community.controller;

import fzb.community.dto.CommentDTO;
import fzb.community.dto.GithubUser;
import fzb.community.dto.QuestionDTO;
import fzb.community.enums.CommentTypeEnum;
import fzb.community.exception.CustomizeErrorCode;
import fzb.community.exception.CustomizeException;
import fzb.community.service.CommentService;
import fzb.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fzb
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id,
                           Model model,
                           HttpServletRequest request){
        Long questionId=null;
        try{
            questionId=Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDTO questionDTO=questionService.getById(questionId);
        model.addAttribute("question", questionDTO);

        List<CommentDTO> commentDTO=commentService.listByTargetId(questionId, CommentTypeEnum.FIRST_COMMENT);
        model.addAttribute("comments", commentDTO);

        return "question";
    }
}
