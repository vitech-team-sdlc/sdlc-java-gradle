package com.vitech.moodfeed.domain.hashtag;

import com.vitech.moodfeed.domain.DomainEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class Hashtag extends DomainEntity {

    Long messageId;
    String tag;

    @Builder
    public Hashtag(Long id, Long messageId, String tag) {
        super(id);
        this.messageId = messageId;
        this.tag = tag;
    }
}
