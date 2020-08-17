package com.vitech.moodfeed.message;

import com.vitech.moodfeed.WebSmallTest;
import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest extends WebSmallTest {

    @MockBean
    private MessageService messageServiceMock;

    @ParameterizedTest
    @MethodSource("messagesLimitOptions")
    void testGetMessages(String limitQuery, int expectedLimit) throws Exception {
        // mock
        List<MessageResponse> expectedResponse = Collections.singletonList(MessageResponse.builder().build());
        when(messageServiceMock.getMessages(anyInt())).thenReturn(expectedResponse);
        // test and verify
        mockMvc()
                .perform(get("/messages" + limitQuery))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(expectedResponse)));
        verify(messageServiceMock).getMessages(eq(expectedLimit));
    }

    private static Stream<Arguments> messagesLimitOptions() {
        return Stream.of(
                Arguments.of("", 10),
                Arguments.of("?limit=5", 5));
    }

    @Test
    void testCreateMessage() throws Exception {
        // mock
        MessageRequest messageRequest = MessageRequest.builder().message("test-message").creatorId(123L).build();
        // test
        mockMvc()
                .perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(messageRequest)))
                .andExpect(status().isOk());
        // verify
        verify(messageServiceMock).createMessage(eq(messageRequest.toMessage()));
    }

}
