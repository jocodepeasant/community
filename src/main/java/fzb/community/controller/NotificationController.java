package fzb.community.controller;

import com.sun.security.ntlm.NTLMException;
import fzb.community.dto.NotificationDTO;
import fzb.community.enums.NotificationTypeEnum;
import fzb.community.model.User;
import fzb.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String notification(@PathVariable("id") Long id,
                               HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("GithubUser");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);
        if (NotificationTypeEnum.REPLAY_COMMENT.getType()==notificationDTO.getType()
        || NotificationTypeEnum.REPLAY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{
            return "redirect:/";
        }
    }
}
