package com.vitech.moodfeed.domain;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Message {

    @Id Long id;
    String message;
    Long creatorId;
    Date createdAt;

}
