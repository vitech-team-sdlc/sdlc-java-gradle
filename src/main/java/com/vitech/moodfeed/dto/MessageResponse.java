package com.vitech.moodfeed.dto;

import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.domain.User;
import lombok.Builder;
import lombok.Value;
import utils.ModelMapperFactory;

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
