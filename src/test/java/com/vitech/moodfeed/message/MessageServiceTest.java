package com.vitech.moodfeed.message;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.hashtag.HashtagService;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageServiceTest extends SmallTest {

    @Mock
    private UserService userServiceMock;

    @Mock
    private HashtagService hashtagServiceMock;

    @Mock
    private MessageRepository messageRepoMock;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testGetMessages() {
        // mock
        List<Message> expectedMessages = TestData.messages();
        when(messageRepoMock.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMessages));
        expectedMessages.forEach(msg -> {
            when(userServiceMock.findById(msg.getCreatorId())).thenReturn(TestData.usersMap().get(msg.getCreatorId()));
            when(hashtagServiceMock.findAllByMessageId(msg.getId())).thenReturn(TestData.hashtagsSet());
        });
        // test
        List<MessageResponse> actualMessages = messageService.getMessages(10);
        // verify
        assertEquals(expectedMessages.size(), actualMessages.size());
        actualMessages.forEach(msg -> {
            assertEquals(TestData.usersMap().get(msg.getCreator().getId()), msg.getCreator());
            assertEquals(TestData.hashtagsSet(), msg.getHashtags());
        });
    }

    @Test
    void testCreateMessage() {
        // mock
        Message message = TestData.message();
        when(messageRepoMock.save(eq(message))).thenReturn(message);
        // test
        messageService.createMessage(message);
        // verify
        verify(messageRepoMock).save(same(message));
        verify(hashtagServiceMock).saveTags(same(message));
    }

}
