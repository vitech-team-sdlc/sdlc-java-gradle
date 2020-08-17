package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private static final String REFERENCED_TEXT = "([a-zA-Z_0-9-.]+)";

    private final HashtagRepository hashtagRepo;

    @Override
    public void saveTags(Message message) {
        extractHashTags(message.getMessage()).stream()
                .map(t -> Hashtag.builder().messageId(message.getId()).tag(t).build())
                .forEach(hashtagRepo::save);
    }

    @Override
    public Set<String> findAllByMessageId(Long messageId) {
        return hashtagRepo.findAllByMessageId(messageId).stream().map(Hashtag::getTag).collect(Collectors.toSet());
    }

    @Override
    public List<String> findTopUsed(int limit) {
        return hashtagRepo.findTopUsed(limit);
    }

    private Set<String> extractHashTags(String message) {
        return extractByPattern('#', message);
    }

    private Set<String> extractMentionedUsers(String message) {
        return extractByPattern('@', message);
    }

    private Set<String> extractByPattern(char start, String message) {
        Set<String> result = new HashSet<>();
        Matcher matcher = Pattern.compile(start + REFERENCED_TEXT).matcher(message);
        while (matcher.find()) {
            String text = matcher.group(0);
            //for some reason both @ and # are a part of a caught group text
            result.add(text.charAt(0) == start ? text.substring(1) : text);
        }
        return result;
    }

}
