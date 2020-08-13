package com.vitech.moodfeed.message;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageServiceTest extends SmallTest {

    private static final int NUMBER_OF_MESSAGES = 5;
    private static final int MESSAGES_LIMIT_LOWER = 4;
    private static final int MESSAGES_LIMIT_HIGHER = 10;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private MessageRepository messageRepositoryMock;

    @InjectMocks
    private MessageServiceImpl messageService;

    @ParameterizedTest
    @ValueSource(ints = { MESSAGES_LIMIT_LOWER, MESSAGES_LIMIT_HIGHER })
    void testGetMessages(int messagesLimit) {
        // mock
        int expectedNumberOfMessages = Math.min(messagesLimit, NUMBER_OF_MESSAGES);
        List<Message> expectedMessages = testMessages().subList(0, expectedNumberOfMessages);
        when(userRepositoryMock.findAllById(anyList())).thenReturn(testUsers());
        when(messageRepositoryMock.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMessages));
        // test
        List<MessageResponse> actualMessages = messageService.getMessages(messagesLimit);
        // verify
        assertEquals(expectedNumberOfMessages, actualMessages.size());
        actualMessages.forEach(msg -> assertNotNull(msg.getCreator()));
    }

    @Test
    void testCreateMessage() {
        // mock
        Message message = testMessage();
        // test
        messageService.createMessage(message);
        // verify
        verify(messageRepositoryMock).save(same(message));
    }

}
