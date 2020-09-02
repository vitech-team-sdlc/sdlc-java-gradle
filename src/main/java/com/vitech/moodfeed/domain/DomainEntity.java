package com.vitech.moodfeed.domain;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

@Value
@NonFinal
public class DomainEntity {

    @Id Long id;

}
