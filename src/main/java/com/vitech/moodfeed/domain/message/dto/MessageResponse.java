package com.vitech.moodfeed.domain.message.dto;

import com.vitech.moodfeed.domain.user.User;
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

}
