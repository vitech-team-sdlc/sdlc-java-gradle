package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.RepoMediumTest;
import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.message.MessageRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

class HashtagRepoIT extends RepoMediumTest {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private HashtagRepo hashtagRepo;

    @AfterEach
    public void cleanup() {
        messageRepo.deleteAll();
        //let's assert tags have been deleted by cascade rules
        assertThat(hashtagRepo.findAll(), is(emptyIterable()));
    }

    @Test
    void findAllByMessageId() {
        //insert messages
        Message m1 = saveNewMessage();
        Message m2 = saveNewMessage();
        //insert hashtags
        saveHashtag(m1, "tag1");
        saveHashtag(m1, "tag5");
        saveHashtag(m2, "tag2");
        //assert
        assertThat(findByMessageId(m1.getId()), containsInAnyOrder("tag1", "tag5"));
        assertThat(findByMessageId(m2.getId()), containsInAnyOrder("tag2"));
        assertThat(findByMessageId(Long.MAX_VALUE), is(emptySet()));
    }

    private Message saveNewMessage() {
        return messageRepo.save(Message.builder().body("anything").creatorId(1L).build());
    }

    private void saveHashtag(Message m, String tag) {
        hashtagRepo.save(Hashtag.builder().messageId(m.getId()).tag(tag).build());
    }

    private Set<String> findByMessageId(long id) {
        return hashtagRepo.findAllByMessageId(id).stream().map(Hashtag::getTag).collect(toSet());
    }

    @Test
    public void findTopUsed() {
        //insert messages
        Message m1 = saveNewMessage();
        //insert hashtags
        saveHashtag(m1, "1", 10);
        saveHashtag(m1, "2", 9);
        saveHashtag(m1, "3", 8);
        saveHashtag(m1, "4", 7);
        saveHashtag(m1, "5", 7);
        saveHashtag(m1, "6", 5);
        saveHashtag(m1, "7", 3);
        saveHashtag(m1, "8", 2);
        saveHashtag(m1, "9", 1);
        //assert
        assertThat(hashtagRepo.findTopUsed(1), is(singletonList("1")));
        assertThat(hashtagRepo.findTopUsed(3), is(asList("1", "2", "3")));
        assertThat(hashtagRepo.findTopUsed(7), is(asList("1", "2", "3", "4", "5", "6", "7")));
        assertThat(hashtagRepo.findTopUsed(1231231), is(asList("1", "2", "3", "4", "5", "6", "7", "8", "9")));
    }

    private void saveHashtag(Message m, String tag, int times) {
        IntStream.range(0, times).forEach(i -> saveHashtag(m, tag));
    }
}