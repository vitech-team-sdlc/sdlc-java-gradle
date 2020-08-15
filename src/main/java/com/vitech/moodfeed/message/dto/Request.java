package com.vitech.moodfeed.message.dto;

import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
@Builder
public class Request {

    private static final String REFERENCED_TEXT = "([a-zA-Z_0-9-.]+)";

    String body;
    Long creatorId;

    public Set<String> extractHashTags() {
        return extractByPattern('#');
    }

    public Set<String> extractMentionedUsers() {
        return extractByPattern('@');
    }

    private Set<String> extractByPattern(char start) {
        Set<String> result = new HashSet<>();
        Matcher matcher = Pattern.compile(start + REFERENCED_TEXT).matcher(body);
        while (matcher.find()) {
            String text = matcher.group(0);
            //for some reason both @ and # are a part of a caught group text
            result.add(text.charAt(0) == start ? text.substring(1) : text);
        }
        return result;
    }
}
