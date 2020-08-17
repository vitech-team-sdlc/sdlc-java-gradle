package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.user.User;
import com.vitech.moodfeed.utils.ModelMapperFactory;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class MessageResponse {

    Long id;
    String message;
    User creator;
    LocalDateTime createdAt;

    public static MessageResponse from(User creator, Message message) {
        return ModelMapperFactory.getInstance().map(message, MessageResponseBuilder.class).creator(creator).build();
    }

}
