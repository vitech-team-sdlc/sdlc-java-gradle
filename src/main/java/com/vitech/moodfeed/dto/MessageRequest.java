package com.vitech.moodfeed.dto;

import com.vitech.moodfeed.domain.Message;
import lombok.Builder;
import lombok.Value;
import utils.ModelMapperFactory;

@Value
@Builder
public class MessageRequest {

    String message;
    Long creatorId;

    public Message toMessage() {
        return ModelMapperFactory.getInstance().map(this, Message.MessageBuilder.class).build();
    }

}
