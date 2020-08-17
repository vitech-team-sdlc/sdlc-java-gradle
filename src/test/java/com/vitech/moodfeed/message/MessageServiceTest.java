package com.vitech.moodfeed.message;

import com.google.common.collect.Maps;
import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.User;
import com.vitech.moodfeed.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
        List<Message> expectedMessages = TestData.messages().subList(0, expectedNumberOfMessages);
        Map<Long, User> users = Maps.uniqueIndex(TestData.users(), User::getId);
        expectedMessages.forEach(msg -> when(userRepositoryMock.findById(msg.getCreatorId()))
                .thenReturn(Optional.of(users.get(msg.getCreatorId()))));
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
        Message message = TestData.message();
        // test
        messageService.createMessage(message);
        // verify
        verify(messageRepositoryMock).save(same(message));
    }

}
