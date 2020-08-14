package com.vitech.moodfeed.message.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Request {

    String body;
    Long creatorId;

}
