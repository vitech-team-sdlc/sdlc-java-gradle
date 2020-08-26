package com.vitech.moodfeed.message;

import com.vitech.moodfeed.hashtag.Hashtag;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
@Builder
public class Message {

    @Id Long id;
    String message;
    Long creatorId;
    LocalDateTime createdAt;

    public static final String SORT_FIELD = "createdAt";
    public static final String SORT_ORDER = "DESC";

    private static final String REFERENCED_TEXT = "([a-zA-Z_0-9-.]+)";

    public Set<Hashtag> extractHashTags() {
        return extractByPattern('#');
    }

    public Set<Hashtag> extractMentionedUsers() {
        return extractByPattern('@');
    }

    private Set<Hashtag> extractByPattern(char start) {
        Set<Hashtag> result = new HashSet<>();
        Matcher matcher = Pattern.compile(start + REFERENCED_TEXT).matcher(message);
        while (matcher.find()) {
            String text = matcher.group(0);
            //for some reason both @ and # are a part of a caught group text
            String tag = text.charAt(0) == start ? text.substring(1) : text;
            result.add(Hashtag.builder().messageId(id).tag(tag).build());
        }
        return result;
    }

}
