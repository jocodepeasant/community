package fzb.community.controller;

import fzb.community.cache.HotTagCache;
import fzb.community.dto.PaginationDTO;
import fzb.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fzb
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "sort", required = false) String sort,
                        @RequestParam(name = "tag", required = false) String tag) {

        PaginationDTO paginationDTO=questionService.list(search,tag,sort,page,size);
        model.addAttribute("pagination", paginationDTO);

        model.addAttribute("tags", hotTagCache.getHots());
        model.addAttribute("sort",sort);
        model.addAttribute("tag",tag);
        model.addAttribute("search", search);

        return "index";
    }
}
