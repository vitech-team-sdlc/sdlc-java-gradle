package com.vitech.moodfeed.dto;

import com.vitech.moodfeed.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    String message;
    Long creatorId;

    public Message toMessage() {
        return Message.builder()
                .message(message)
                .creatorId(creatorId)
                .build();
    }

}
