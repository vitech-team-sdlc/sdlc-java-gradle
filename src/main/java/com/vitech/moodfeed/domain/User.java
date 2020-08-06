package com.vitech.moodfeed.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class User {

    UUID id;
    String firstName;
    String lastName;
    String logoColor;

}
