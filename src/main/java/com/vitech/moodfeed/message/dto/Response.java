package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.user.User;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.Set;

@Value
@Builder
public class Response {

    Long id;
    String body;
    User creator;
    Date createdAt;
    Set<String> hashtags;

}
