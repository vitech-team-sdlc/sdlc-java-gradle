package com.vitech.moodfeed.domain.message.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageRequest {

    String message;
    Long creatorId;

}
