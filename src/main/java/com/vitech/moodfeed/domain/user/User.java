package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.domain.DomainEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class User extends DomainEntity {

    String firstName;
    String lastName;
    String logoColor;

    @Builder
    public User(Long id, String firstName, String lastName, String logoColor) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.logoColor = logoColor;
    }
}
