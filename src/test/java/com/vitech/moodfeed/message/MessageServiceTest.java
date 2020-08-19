package com.vitech.moodfeed.message;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.hashtag.Hashtag;
import com.vitech.moodfeed.hashtag.HashtagRepository;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageServiceTest extends SmallTest {

    @Mock
    private UserRepository userRepoMock;

    @Mock
    private MessageRepository messageRepoMock;

    @Mock
    private HashtagRepository hashtagRepoMock;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testGetMessages() {
        // mock
        List<Message> expectedMessages = TestData.messages();
        when(messageRepoMock.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMessages));
        expectedMessages.forEach(msg -> {
            Long creator = msg.getCreatorId();
            when(userRepoMock.findById(creator)).thenReturn(Optional.of(TestData.usersMap().get(creator)));
            when(hashtagRepoMock.findAllByMessageId(msg.getId())).thenReturn(TestData.hashtags());
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

    @ParameterizedTest
    @MethodSource("sourceCreateMessage")
    void testCreateMessage(String messageText, List<String> expectedTags) {
        // mock
        Message message = Message.builder().id(1L).message(messageText).build();
        when(messageRepoMock.save(eq(message))).thenReturn(message);
        // test
        messageService.createMessage(message);
        // verify
        verify(messageRepoMock).save(same(message));
        expectedTags.forEach(t -> verify(hashtagRepoMock).save(eq(Hashtag.builder().messageId(1L).tag(t).build())));
    }

    private static Stream<Arguments> sourceCreateMessage() {
        return Stream.of(
                Arguments.of("Some text #tag1 or #tag__2 #t_.-op", Arrays.asList("tag1", "tag__2", "t_.-op")),
                Arguments.of("#Some text tag1 or tag__2 or #___tag3___", Arrays.asList("Some", "___tag3___"))
        );
    }

}
