package fzb.community.controller;

import fzb.community.cache.TagCache;
import fzb.community.dto.QuestionDTO;
import fzb.community.model.Question;
import fzb.community.model.User;
import fzb.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fzb
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(HttpServletRequest request,
                       Model model,
                       @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("GithubUser");
        if (user == null || user.getId() == null) {
            model.addAttribute("msg", "用户未登录，请先登录");
        } else {
            QuestionDTO question = questionService.getById(id);
            model.addAttribute("title", question.getTitle());
            model.addAttribute("description", question.getDescription());
            model.addAttribute("tag", question.getTag());
            model.addAttribute("id", question.getId());
        }
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,
                          Model model) {
        User user = (User) request.getSession().getAttribute("GithubUser");
        if (user == null || user.getId() == null) {
            model.addAttribute("msg", "用户未登录，请先登录");
        }

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "description", required = false) String description,
                            @RequestParam(name = "tag", required = false) String tag,
                            @RequestParam(name = "id",required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("id",id);
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        User user = (User) request.getSession().getAttribute("GithubUser");
        if (user == null || user.getId() == null) {
            model.addAttribute("msg", "用户未登录，请先登录");
            return "publish";
        }
        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("msg", "请输入正确的标题");
            return "publish";
        }
        if (description == null || description.trim().isEmpty()) {
            model.addAttribute("msg", "请输入正确的问题描述");
            return "publish";
        }
        if (tag == null || tag.trim().isEmpty()) {
            model.addAttribute("msg", "请输入至少一个标签");
            return "publish";
        }
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
