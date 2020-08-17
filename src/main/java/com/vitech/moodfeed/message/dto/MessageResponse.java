package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.user.User;
import com.vitech.moodfeed.utils.ModelMapperFactory;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class MessageResponse {

    Long id;
    String message;
    User creator;
    LocalDateTime createdAt;
    Set<String> hashtags;

    public static MessageResponse from(Message message, User creator, Set<String> hashtags) {
        return ModelMapperFactory.getInstance()
                .map(message, MessageResponseBuilder.class)
                .creator(creator)
                .hashtags(hashtags)
                .build();
    }

}
