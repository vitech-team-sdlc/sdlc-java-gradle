package com.vitech.moodfeed.message.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageRequest {

    String body;
    Long creatorId;

}
