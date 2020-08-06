package com.vitech.moodfeed.domain;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class Message {

    String message;
    UUID createdBy;
    Date createdAt;

}
