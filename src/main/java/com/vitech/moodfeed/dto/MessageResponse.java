package com.vitech.moodfeed.dto;

import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.domain.User;
import lombok.Data;

import java.util.Date;

@Data
public class MessageResponse {

    Long id;
    String message;
    User creator;
    Date createdAt;

    public static MessageResponse from(User creator, Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(message.getId());
        messageResponse.setMessage(message.getMessage());
        messageResponse.setCreatedAt(message.getCreatedAt());
        messageResponse.setCreator(creator);
        return messageResponse;
    }

}
