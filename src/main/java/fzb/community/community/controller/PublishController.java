package fzb.community.community.controller;

import fzb.community.community.model.Question;
import fzb.community.community.model.User;
import fzb.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,Model model) {
        User user= (User) request.getSession().getAttribute("GithubUser");
        if (user==null || user.getId()==null){
            model.addAttribute("msg", "用户未登录，请先登录");
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title",required = false) String title,
                            @RequestParam(name = "description",required = false) String description,
                            @RequestParam(name = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        User user= (User) request.getSession().getAttribute("GithubUser");
        if (user==null || user.getId()==null){
            model.addAttribute("msg", "用户未登录，请先登录");
            return "publish";
        }
        if (title==null || title.trim().isEmpty()){
            model.addAttribute("msg", "请输入正确的标题");
            return "publish";
        }
        if (description==null || description.trim().isEmpty()){
            model.addAttribute("msg", "请输入正确的问题描述");
            return "publish";
        }
        if (tag==null || tag.trim().isEmpty()){
            model.addAttribute("msg", "请输入至少一个标签");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
