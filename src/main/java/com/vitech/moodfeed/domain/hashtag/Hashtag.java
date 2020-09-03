package com.vitech.moodfeed.domain.hashtag;

import com.vitech.moodfeed.domain.DomainEntity;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
@DomainEntity
public class Hashtag {

    @Id Long id;
    Long messageId;
    String tag;

}
