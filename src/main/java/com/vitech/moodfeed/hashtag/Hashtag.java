package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.RepoRegistry;
import com.vitech.moodfeed.message.dto.Request;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Builder
@Value
public class Hashtag {

    @Id
    Long id;
    Long messageId;
    String tag;

    public static void saveTags(Long messageId, Request request, RepoRegistry registry) {
        request.extractHashTags().stream()
                .map(t -> Hashtag.builder().messageId(messageId).tag(t).build())
                .forEach(t -> {
                    Hashtag saved = registry.getHashtagRepo().save(t);
                    log.info("{} has been saved", saved);
                });
    }

    public static Set<String> findAllByMessageId(Long messageId, RepoRegistry registry) {
        return registry.getHashtagRepo().findAllByMessageId(messageId).stream().map(Hashtag::getTag).collect(toSet());
    }
}
