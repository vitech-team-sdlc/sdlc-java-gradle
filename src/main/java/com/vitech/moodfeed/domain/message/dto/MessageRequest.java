package com.vitech.moodfeed.domain.message.dto;

import com.vitech.moodfeed.domain.message.Message;
import lombok.Builder;
import lombok.Value;
import com.vitech.moodfeed.utils.ModelMapperFactory;

@Value
@Builder
public class MessageRequest {

    String message;
    Long creatorId;

    public Message toMessage() {
        return ModelMapperFactory.getInstance().map(this, Message.MessageBuilder.class).build();
    }

}
