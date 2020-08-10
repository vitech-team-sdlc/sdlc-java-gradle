package com.vitech.moodfeed.dto;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MessageResponseTest extends SmallTest {

    @Test
    void testFromMessage() {
        // mock
        User user = User.builder()
                .id(123L)
                .firstName("f-name")
                .lastName("l-name")
                .logoColor("#000000")
                .build();
        Message message = Message.builder()
                .id(111L)
                .message("test-message")
                .createdAt(new Date())
                .creatorId(123L)
                .build();
        // test
        MessageResponse messageResponse = MessageResponse.from(user, message);
        // verify
        assertEquals(message.getId(), messageResponse.getId());
        assertEquals(message.getMessage(), messageResponse.getMessage());
        assertEquals(message.getCreatedAt(), messageResponse.getCreatedAt());
        assertSame(user, messageResponse.getCreator());
    }
}
