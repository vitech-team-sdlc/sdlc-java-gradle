package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MessageResponseTest extends SmallTest {

    @Test
    void testFromMessage() {
        // mock
        User user = testUser();
        Message message = testMessage();
        // test
        MessageResponse messageResponse = MessageResponse.from(user, message);
        // verify
        assertEquals(message.getId(), messageResponse.getId());
        assertEquals(message.getMessage(), messageResponse.getMessage());
        assertEquals(message.getCreatedAt(), messageResponse.getCreatedAt());
        assertSame(user, messageResponse.getCreator());
    }
}
