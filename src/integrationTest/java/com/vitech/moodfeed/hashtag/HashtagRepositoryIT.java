package com.vitech.moodfeed.hashtag;

import com.google.common.collect.Lists;
import com.vitech.moodfeed.RepoMediumTest;
import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.message.MessageRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashtagRepositoryIT extends RepoMediumTest<Hashtag> {

    private final HashtagRepository hashtagRepo;

    @Autowired
    public HashtagRepositoryIT(HashtagRepository hashtagRepo) {
        super(hashtagRepo, new CrudTestDataProvider<>(
                Hashtag::getId,
                () -> Hashtag.builder().messageId(1L).tag("tag").build(),
                (id) -> Hashtag.builder().id(id).messageId(1L).tag("updated-tag").build()
        ));
        this.hashtagRepo = hashtagRepo;
    }

    @BeforeAll
    static void beforeAll(@Autowired MessageRepository messageRepo) {
        messageRepo.save(Message.builder().message("test-message-1").creatorId(1L).build());
        messageRepo.save(Message.builder().message("test-message-2").creatorId(2L).build());
    }

    @AfterAll
    static void afterAll(@Autowired MessageRepository messageRepo) {
        messageRepo.deleteAll();
    }

    @Test
    void testFindAllByMessageId() {
        // prepare
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag1").build());
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag2").build());
        hashtagRepo.save(Hashtag.builder().messageId(2L).tag("tag3").build());
        // test
        assertEquals(2, Lists.newArrayList(hashtagRepo.findAllByMessageId(1L)).size());
        assertEquals(1, Lists.newArrayList(hashtagRepo.findAllByMessageId(2L)).size());
    }

    @Test
    void testFindTopUsed() {
        // prepare
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag1").build());
        hashtagRepo.save(Hashtag.builder().messageId(2L).tag("tag2").build());
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag2").build());
        hashtagRepo.save(Hashtag.builder().messageId(2L).tag("tag3").build());
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag3").build());
        hashtagRepo.save(Hashtag.builder().messageId(1L).tag("tag3").build());
        // test
        assertEquals(Arrays.asList("tag3", "tag2"), hashtagRepo.findTopUsed(2));
    }

}
