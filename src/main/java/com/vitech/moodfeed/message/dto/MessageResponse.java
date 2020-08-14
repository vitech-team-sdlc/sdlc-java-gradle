package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.user.User;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class MessageResponse {

    Long id;
    String body;
    User creator;
    Date createdAt;

}
