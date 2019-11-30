package fzb.community.service;

import fzb.community.dto.NotificationDTO;
import fzb.community.dto.PaginationDTO;
import fzb.community.enums.NotificationTypeEnum;
import fzb.community.mapper.NotificationMapper;
import fzb.community.model.Notification;
import fzb.community.model.NotificationExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        PaginationDTO<NotificationDTO> paginationDTO=new PaginationDTO<>();
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
        if (notifications.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for (Notification notification:notifications){
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }
}