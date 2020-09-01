package com.vitech.moodfeed.domain.message.dto;

import com.vitech.moodfeed.domain.hashtag.Hashtag;
import com.vitech.moodfeed.domain.message.Message;
import com.vitech.moodfeed.domain.user.User;
import com.vitech.moodfeed.utils.ModelMapperFactory;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class MessageResponse {

    Long id;
    String message;
    User creator;
    LocalDateTime createdAt;
    Set<String> hashtags;

    public static MessageResponse from(Message message, User creator, Set<Hashtag> hashtags) {
        return ModelMapperFactory.getInstance()
                .map(message, MessageResponseBuilder.class)
                .creator(creator)
                .hashtags(hashtags.stream().map(Hashtag::getTag).collect(Collectors.toSet()))
                .build();
    }

}
