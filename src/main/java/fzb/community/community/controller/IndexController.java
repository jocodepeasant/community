package fzb.community.community.controller;

import fzb.community.community.dto.PaginationDTO;
import fzb.community.community.dto.QuestionDTO;
import fzb.community.community.service.IndexService;
import fzb.community.community.service.PaginationService;
import fzb.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private PaginationService paginationService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "10") Integer size) {
        List<QuestionDTO> list = indexService.list(page,size);
        model.addAttribute("list", list);

        PaginationDTO pagination = paginationService.pagination(page);
        model.addAttribute("pagination", pagination);

        return "index";
    }
}
