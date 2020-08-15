package com.vitech.moodfeed.message;

import com.vitech.moodfeed.RepoRegistry;
import com.vitech.moodfeed.hashtag.HashtagRepo;
import com.vitech.moodfeed.message.dto.Request;
import com.vitech.moodfeed.message.dto.Response;
import com.vitech.moodfeed.user.UserRepo;
import com.vitech.moodfeed.user.UserTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@Tag("smallTest")
@ExtendWith(MockitoExtension.class)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class MessageTest {

    private static final int NUMBER_OF_MESSAGES = 5;
    private static final int MESSAGES_LIMIT_LOWER = 4;
    private static final int MESSAGES_LIMIT_HIGHER = 10;

    @Mock
    private UserRepo userRepo;

    @Mock
    private MessageRepo messageRepo;

    @Mock
    private HashtagRepo hashtagRepo;

    @InjectMocks
    private RepoRegistry repoRegistry;

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
    void testGetNewest(int messagesLimit) {
        // mock
        initUserMocks();
        int expectedNumberOfMessages = Math.min(messagesLimit, NUMBER_OF_MESSAGES);
        List<Message> expectedMessages = messages().subList(0, expectedNumberOfMessages);
        when(messageRepo.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedMessages));
        // test
        List<Response> actualMessages = Message.getNewest(
                PageRequest.of(0, messagesLimit, Sort.by(Sort.Direction.DESC, "createdAt")),
                repoRegistry
        );
        // verify
        assertEquals(expectedNumberOfMessages, actualMessages.size());
        actualMessages.forEach(msg -> assertNotNull(msg.getCreator()));
    }

    @Test
    void testToResponse() {
        // mock
        initUserMocks();
        Message message = messages().get(0);
        // test
        Response response = message.toResponse(repoRegistry);
        // verify
        assertEquals(message.getId(), response.getId());
        assertEquals(message.getBody(), response.getBody());
        assertEquals(message.getCreatedAt(), response.getCreatedAt());
        assertNotNull(response.getCreator());
    }

    @Test
    public void testSave() {
        when(messageRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        when(hashtagRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        String body = "-test- #tag1 and #tag2";
        long creatorId = 1L;
        Request request = Request.builder().body(body).creatorId(creatorId).build();
        Message.save(request, repoRegistry);
        verify(messageRepo, times(1))
                .save(Message.builder().body(body).creatorId(creatorId).build());
        verify(hashtagRepo, times(2)).save(any());
    }

    private void initUserMocks() {
        when(userRepo.findById(anyLong())).thenAnswer(
                i -> UserTest.users().stream().filter(u -> u.getId().equals(i.getArgument(0))).findFirst()
        );
    }
}
