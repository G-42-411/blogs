package com.dreamland.service;

import com.dreamland.dto.NotificationDTO;
import com.dreamland.dto.PaginationDTO;
import com.dreamland.dto.QuestionDTO;
import com.dreamland.enums.NotificationStatusEnum;
import com.dreamland.enums.NotificationTypeEnum;
import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.exception.CustomizeException;
import com.dreamland.mapper.NotificationMapper;
import com.dreamland.pojo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        Integer questionCount = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > questionCount)
            page = questionCount;
        Integer offset = 0;
        if(page != 0) {
            offset = size * (page - 1);
        }
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);

        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        if (notifications.size() == 0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setPagination(totalCount, page, size);
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userid) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userid)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
//        Notification notification = notificationMapper.selectByPrimaryKey(id);
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;

    }
}
