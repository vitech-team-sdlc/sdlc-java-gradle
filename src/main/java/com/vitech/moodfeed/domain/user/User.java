package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder(toBuilder = true)
@DomainEntity
public class User {

    @Id Long id;
    String authId;
    String firstName;
    String lastName;
    String logoColor;

}
