package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.TestData;
import com.vitech.moodfeed.WebSmallTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HashtagController.class)
public class HashtagControllerTest extends WebSmallTest {

    @MockBean
    private HashtagService hashtagServiceMock;

    @ParameterizedTest
    @MethodSource("sourceFindTopUsed")
    void testFindTopUsed(String limitQuery, int expectedLimit) throws Exception {
        // mock
        List<String> expectedResponse = TestData.hashtagsList();
        when(hashtagServiceMock.findTopUsed(anyInt())).thenReturn(expectedResponse);
        // test and verify
        mockMvc()
                .perform(get("/hashtags" + limitQuery))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(expectedResponse)));
        verify(hashtagServiceMock).findTopUsed(eq(expectedLimit));
    }

    private static Stream<Arguments> sourceFindTopUsed() {
        return Stream.of(
                Arguments.of("", 10),
                Arguments.of("?limit=5", 5));
    }
}
