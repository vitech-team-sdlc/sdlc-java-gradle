package com.vitech.moodfeed.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Value
public class Message {

    @Id long id;
    String message;
    long creatorId;
    Date createdAt;

}
