package com.vitech.moodfeed.message;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Value
@Builder
public class Message {

    @Id Long id;
    String message;
    Long creatorId;
    Date createdAt;

}
