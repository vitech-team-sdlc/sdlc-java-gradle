package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.message.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HashtagServiceTest extends SmallTest {

    @Mock
    private HashtagRepository hashtagRepoMock;

    @InjectMocks
    private HashtagServiceImpl hashtagService;

    @ParameterizedTest
    @MethodSource("sourceSaveTags")
    void testSaveTags(String messageText, List<String> expectedTags) {
        // mock
        Message message = Message.builder().id(1L).message(messageText).build();
        // test
        hashtagService.saveTags(message);
        // verify
        expectedTags.forEach(t -> verify(hashtagRepoMock).save(eq(Hashtag.builder().messageId(1L).tag(t).build())));
    }

    private static Stream<Arguments> sourceSaveTags() {
        return Stream.of(
                Arguments.of("Some text #tag1 or #tag__2 #t_.-op", Arrays.asList("tag1", "tag__2", "t_.-op")),
                Arguments.of("#Some text tag1 or tag__2 or #___tag3___", Arrays.asList("Some", "___tag3___"))
        );
    }

    @Test
    void testFindAllByMessageId() {
        // mock
        when(hashtagRepoMock.findAllByMessageId(1L)).thenReturn(TestData.hashtags());
        // test
        Set<String> hashtags = hashtagService.findAllByMessageId(1L);
        // verify
        assertEquals(TestData.hashtagsSet(), hashtags);
    }

    @Test
    void testFindTopUsed() {
        // mock
        when(hashtagRepoMock.findTopUsed(eq(10))).thenReturn(TestData.hashtagsList());
        // test
        List<String> hashtags = hashtagService.findTopUsed(10);
        // verify
        assertEquals(TestData.hashtagsList(), hashtags);
    }

}
