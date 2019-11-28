package fzb.community.controller;

import fzb.community.dto.PaginationDTO;
import fzb.community.dto.QuestionDTO;
import fzb.community.service.IndexService;
import fzb.community.service.PaginationService;
import fzb.community.service.QuestionService;
import fzb.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fzb
 */
@Controller
public class IndexController {

    static int a;

    @Autowired
    private IndexService indexService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PaginationService paginationService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "sort", required = false) String sort,
                        @RequestParam(name = "tag", required = false) String tag) {
        List<QuestionDTO> list = indexService.list(page, size);
        model.addAttribute("list", list);

        PaginationDTO pagination = paginationService.pagination(page);
        model.addAttribute("pagination", pagination);



        return "index";
    }
}
