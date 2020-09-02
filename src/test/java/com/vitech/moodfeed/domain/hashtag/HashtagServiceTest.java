package com.vitech.moodfeed.domain.hashtag;

import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.TestData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class HashtagServiceTest extends SmallTest {

    @Mock
    private HashtagRepository hashtagRepoMock;

    @InjectMocks
    private HashtagService hashtagService;

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
