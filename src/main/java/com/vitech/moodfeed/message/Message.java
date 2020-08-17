package com.vitech.moodfeed.message;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Value
@Builder
public class Message {

    @Id Long id;
    String message;
    Long creatorId;
    LocalDateTime createdAt;

}
