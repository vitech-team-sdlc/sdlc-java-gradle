package com.vitech.moodfeed.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class User {

    @Id long id;
    String firstName;
    String lastName;
    String logoColor;

}
