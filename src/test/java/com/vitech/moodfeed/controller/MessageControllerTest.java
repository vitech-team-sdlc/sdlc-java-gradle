package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.dto.MessageRequest;
import com.vitech.moodfeed.dto.MessageResponse;
import com.vitech.moodfeed.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageControllerTest extends SmallTest {

    @Mock
    private MessageService messageServiceMock;

    @InjectMocks
    private MessageController messageController;

    @Test
    void testGetMessages() {
        // mock
        List<MessageResponse> expectedResponse = Collections.singletonList(new MessageResponse());
        when(messageServiceMock.getMessages(anyInt())).thenReturn(expectedResponse);
        // test
        List<MessageResponse> actualResponse = messageController.getMessages(10);
        // verify
        assertSame(expectedResponse, actualResponse);
    }

    @Test
    void testCreateMessage() {
        // mock
        MessageRequest messageRequest = new MessageRequest("test-message", 123L);
        // test
        messageController.createMessage(messageRequest);
        // verify
        verify(messageServiceMock).createMessage(eq(messageRequest.toMessage()));
    }

}
