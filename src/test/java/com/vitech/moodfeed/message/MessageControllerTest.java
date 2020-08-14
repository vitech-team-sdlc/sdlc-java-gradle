package com.vitech.moodfeed.message;

import com.vitech.moodfeed.WebSmallTest;
import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.user.User;
import com.vitech.moodfeed.user.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    private MessageRepository messageRepo;

    @MockBean
    private UserRepository userRepository;

    @SneakyThrows
    @Test
    void testGetMessages() {
        // mock
        long creatorId = 1L;
        Message message = Message.builder().creatorId(creatorId).build();
        when(userRepository.findById(creatorId)).thenReturn(Optional.of(User.builder().id(creatorId).build()));
        when(messageRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(message)));
        // test and verify
        mockMvc()
                .perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(Collections.singletonList(message.toResponse(userRepository)))));
    }

    @SneakyThrows
    @Test
    void testCreateMessage() {
        // mock
        MessageRequest messageRequest = MessageRequest.builder().body("test-message").creatorId(123L).build();
        // test
        mockMvc()
                .perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(messageRequest)))
                .andExpect(status().isOk());
        // verify
        verify(messageRepo).save(eq(Message.fromRequest(messageRequest)));
    }

}
