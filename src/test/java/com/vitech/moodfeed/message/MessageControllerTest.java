package com.vitech.moodfeed.message;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
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
        List<MessageResponse> expectedResponse = Collections.singletonList(MessageResponse.builder().build());
        when(messageServiceMock.getMessages(anyInt())).thenReturn(expectedResponse);
        // test
        List<MessageResponse> actualResponse = messageController.getMessages(10);
        // verify
        assertSame(expectedResponse, actualResponse);
    }

    @Test
    void testCreateMessage() {
        // mock
        MessageRequest messageRequest = MessageRequest.builder().message("test-message").creatorId(123L).build();
        // test
        messageController.createMessage(messageRequest);
        // verify
        verify(messageServiceMock).createMessage(eq(messageRequest.toMessage()));
    }

}
