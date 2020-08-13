package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.user.User;
import lombok.Builder;
import lombok.Value;
import com.vitech.moodfeed.utils.ModelMapperFactory;

import java.util.Date;

@Value
@Builder
public class MessageResponse {

    Long id;
    String message;
    User creator;
    Date createdAt;

    public static MessageResponse from(User creator, Message message) {
        return ModelMapperFactory.getInstance().map(message, MessageResponseBuilder.class).creator(creator).build();
    }

}
