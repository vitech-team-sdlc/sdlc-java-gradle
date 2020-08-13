package com.vitech.moodfeed.message.dto;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.message.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageRequestTest extends SmallTest {

    @Test
    void testToMessage() {
        // mock
        MessageRequest messageRequest = new MessageRequest("test-message", 123L);
        // test
        Message message = messageRequest.toMessage();
        // verify
        assertNull(message.getId());
        assertNull(message.getCreatedAt());
        assertEquals(messageRequest.getMessage(), message.getMessage());
        assertEquals(message.getCreatorId(), message.getCreatorId());
    }

}
