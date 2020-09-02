package com.vitech.moodfeed;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vitech.moodfeed.domain.hashtag.Hashtag;
import com.vitech.moodfeed.domain.message.Message;
import com.vitech.moodfeed.domain.user.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestData {

    public static List<User> users() {
        return Arrays.asList(
                User.builder().id(1L).firstName("Homer").lastName("Simpson").logoColor("#faf7af").build(),
                User.builder().id(2L).firstName("Marge").lastName("Simpson").logoColor("#b167e3").build(),
                User.builder().id(3L).firstName("Bart").lastName("Simpson").logoColor("#fad8af").build(),
                User.builder().id(4L).firstName("Lisa").lastName("Simpson").logoColor("#f0c5eb").build(),
                User.builder().id(5L).firstName("Ned").lastName("Flanders").logoColor("#afe0fa").build()
        );
    }

    public static Map<Long, User> usersMap() {
        return Maps.uniqueIndex(users(), User::getId);
    }

    public static User user() {
        return users().get(0);
    }

    public static List<Message> messages() {
        return Arrays.asList(
                Message.builder().id(101L).message("message-1").creatorId(1L).createdAt(LocalDateTime.now()).build(),
                Message.builder().id(102L).message("message-2").creatorId(2L).createdAt(LocalDateTime.now()).build(),
                Message.builder().id(103L).message("message-3").creatorId(3L).createdAt(LocalDateTime.now()).build(),
                Message.builder().id(104L).message("message-4").creatorId(4L).createdAt(LocalDateTime.now()).build(),
                Message.builder().id(105L).message("message-5").creatorId(5L).createdAt(LocalDateTime.now()).build()
        );
    }

    public static Message message() {
        return messages().get(0);
    }

    public static Set<Hashtag> hashtags() {
        return Sets.newHashSet(
                Hashtag.builder().id(1L).messageId(1L).tag("tag1").build(),
                Hashtag.builder().id(2L).messageId(2L).tag("tag2").build(),
                Hashtag.builder().id(3L).messageId(3L).tag("tag3").build(),
                Hashtag.builder().id(4L).messageId(4L).tag("tag4").build(),
                Hashtag.builder().id(5L).messageId(5L).tag("tag5").build()
        );
    }

    public static Set<String> hashtagsSet() {
        return hashtags().stream().map(Hashtag::getTag).collect(Collectors.toSet());
    }

    public static List<String> hashtagsList() {
        return Lists.newArrayList(hashtagsSet());
    }

}
