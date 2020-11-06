package com.vitech.moodfeed.domain.message;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.domain.hashtag.Hashtag;
import com.vitech.moodfeed.domain.message.dto.MessageRequest;
import com.vitech.moodfeed.domain.message.dto.MessageResponse;
import com.vitech.moodfeed.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageTest extends SmallTest {

    @Test
    void testFromRequest() {
        // mock
        MessageRequest messageRequest = MessageRequest.builder().message("test-message").creatorId(123L).build();
        // test
        Message message = Message.fromRequest(messageRequest);
        // verify
        assertNull(message.getId());
        assertNull(message.getCreatedAt());
        assertEquals(messageRequest.getMessage(), message.getMessage());
        assertEquals(message.getCreatorId(), message.getCreatorId());
    }

    @Test
    void testToResponse() {
        // mock
        User user = TestData.user();
        Message message = TestData.message();
        Set<Hashtag> hashtags = TestData.hashtags();
        // test
        MessageResponse messageResponse = message.toResponse(user, hashtags);
        // verify
        assertEquals(message.getId(), messageResponse.getId());
        assertEquals(message.getMessage(), messageResponse.getMessage());
        assertEquals(message.getCreatedAt(), messageResponse.getCreatedAt());
        assertSame(user, messageResponse.getCreator());
        assertEquals(TestData.hashtagsSet(), messageResponse.getHashtags());
    }

    @Test
    void testExtractHashTags() {
        long testMessageId = 101L;
        Message testMessage = Message.builder()
                .id(testMessageId)
                .message("Massage with #tag1 and #tag2 and @user1")
                .build();
        Set<Hashtag> hashtags = testMessage.extractHashTags();
        assertEquals(2, hashtags.size());
        assertTrue(hashtags.contains(Hashtag.builder().messageId(testMessageId).tag("tag1").build()));
        assertTrue(hashtags.contains(Hashtag.builder().messageId(testMessageId).tag("tag2").build()));
    }

    @Test
    void testExtractMentionedUsers() {
        long testMessageId = 101L;
        Message testMessage = Message.builder()
                .id(testMessageId)
                .message("Massage with @user1 and @user2 and #tag1")
                .build();
        Set<Hashtag> hashtags = testMessage.extractMentionedUsers();
        assertEquals(2, hashtags.size());
        assertTrue(hashtags.contains(Hashtag.builder().messageId(testMessageId).tag("user1").build()));
        assertTrue(hashtags.contains(Hashtag.builder().messageId(testMessageId).tag("user2").build()));
    }

}
