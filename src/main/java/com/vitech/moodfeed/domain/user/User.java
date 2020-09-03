package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
@DomainEntity
public class User {

    @Id Long id;
    String firstName;
    String lastName;
    String logoColor;

}
