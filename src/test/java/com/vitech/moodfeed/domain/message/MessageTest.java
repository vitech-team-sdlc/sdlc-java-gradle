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

public class MessageTest extends SmallTest {

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

}
