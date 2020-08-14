package com.vitech.moodfeed.message;

import com.vitech.moodfeed.WebMediumTest;
import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageControllerIT extends WebMediumTest {

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void beforeEach() {
        messageRepository.deleteAll();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 7})
    void testCreateAndGetMessages(int limit) {

        // generate messages
        List<MessageRequest> messageRequests = LongStream.range(1, 5)
                .mapToObj(num -> MessageRequest.builder().body("test-message" + num).creatorId(num).build())
                .collect(Collectors.toList());

        // create generated messages by REST API and verify response is OK
        messageRequests.forEach(messageRequest -> assertEquals(
                HttpStatus.OK,
                restTemplate().postForEntity(messagesUri(), messageRequest, Void.class).getStatusCode()));

        // get all messages by REST API
        ResponseEntity<List<MessageResponse>> createdMessages = restTemplate().exchange(
                RequestEntity.get(messagesUriWithLimit(limit)).build(),
                new ParameterizedTypeReference<List<MessageResponse>>() {});

        // verify previously created messages are present and limit is working as expected
        assertEquals(HttpStatus.OK, createdMessages.getStatusCode());
        assertNotNull(createdMessages.getBody());
        assertEquals(Math.min(limit, messageRequests.size()), createdMessages.getBody().size());
    }

    private URI messagesUri() {
        return messagesUriWithLimit(null);
    }

    private URI messagesUriWithLimit(Integer limit) {
        return URI.create(baseUrl() + "/messages" + (Objects.nonNull(limit) ? "?limit=" + limit : ""));
    }

}
