package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.hashtag.Hashtag;
import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.user.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MessageResponseTest extends SmallTest {

    @Test
    void testFromMessage() {
        // mock
        User user = TestData.user();
        Message message = TestData.message();
        Set<Hashtag> hashtags = TestData.hashtags();
        // test
        MessageResponse messageResponse = MessageResponse.from(message, user, hashtags);
        // verify
        assertEquals(message.getId(), messageResponse.getId());
        assertEquals(message.getMessage(), messageResponse.getMessage());
        assertEquals(message.getCreatedAt(), messageResponse.getCreatedAt());
        assertSame(user, messageResponse.getCreator());
        assertEquals(TestData.hashtagsSet(), messageResponse.getHashtags());
    }
}
