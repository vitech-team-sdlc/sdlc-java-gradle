package com.vitech.moodfeed.message;

import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
import com.vitech.moodfeed.user.UserTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class MessageTest {

    private static final int NUMBER_OF_MESSAGES = 5;
    private static final int MESSAGES_LIMIT_LOWER = 4;
    private static final int MESSAGES_LIMIT_HIGHER = 10;

    @Mock
    private UserRepository userRepo;

    @Mock
    private MessageRepository messageRepo;

    public List<Message> messages() {
        return Arrays.asList(
                Message.builder().id(101L).body("test-message-1").creatorId(1L).createdAt(new Date()).build(),
                Message.builder().id(102L).body("test-message-2").creatorId(2L).createdAt(new Date()).build(),
                Message.builder().id(103L).body("test-message-3").creatorId(3L).createdAt(new Date()).build(),
                Message.builder().id(104L).body("test-message-4").creatorId(4L).createdAt(new Date()).build(),
                Message.builder().id(105L).body("test-message-5").creatorId(5L).createdAt(new Date()).build()
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {MESSAGES_LIMIT_LOWER, MESSAGES_LIMIT_HIGHER})
    void testGetMessages(int messagesLimit) {
        // mock
        initUserMocks();
        int expectedNumberOfMessages = Math.min(messagesLimit, NUMBER_OF_MESSAGES);
        List<Message> expectedMessages = messages().subList(0, expectedNumberOfMessages);
        when(messageRepo.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMessages));
        // test
        List<MessageResponse> actualMessages = Message.getNewest(messagesLimit, messageRepo, userRepo);
        // verify
        assertEquals(expectedNumberOfMessages, actualMessages.size());
        actualMessages.forEach(msg -> assertNotNull(msg.getCreator()));
    }

    @Test
    void testToMessage() {
        // mock
        MessageRequest messageRequest = MessageRequest.builder().body("test-message").creatorId(123L).build();
        // test
        Message message = Message.fromRequest(messageRequest);
        // verify
        assertNull(message.getId());
        assertNull(message.getCreatedAt());
        assertEquals(messageRequest.getBody(), message.getBody());
        assertEquals(message.getCreatorId(), message.getCreatorId());
    }

    @Test
    void testFromMessage() {
        // mock
        initUserMocks();
        Message message = messages().get(0);
        // test
        MessageResponse messageResponse = message.toResponse(userRepo);
        // verify
        assertEquals(message.getId(), messageResponse.getId());
        assertEquals(message.getBody(), messageResponse.getBody());
        assertEquals(message.getCreatedAt(), messageResponse.getCreatedAt());
        assertNotNull(messageResponse.getCreator());
    }

    private void initUserMocks() {
        when(userRepo.findById(anyLong())).thenAnswer(
                i -> UserTest.users().stream().filter(u -> u.getId().equals(i.getArgument(0))).findFirst()
        );
    }
}
