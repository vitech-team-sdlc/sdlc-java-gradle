package com.vitech.moodfeed.message;

import com.vitech.moodfeed.WebMediumTest;
import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageControllerIT extends WebMediumTest {

    @Test
    void testCreateAndGetMessages() {
        // generate messages
        List<MessageRequest> messageRequests = LongStream.range(1, 5)
                .mapToObj(num -> MessageRequest.builder().message("test-message" + num).creatorId(num).build())
                .collect(Collectors.toList());

        // create generated messages by REST API and verify response is OK
        messageRequests.forEach(messageRequest -> assertEquals(
                HttpStatus.OK,
                restTemplate().postForEntity("/messages", messageRequest, Void.class).getStatusCode()));

        // get all messages by REST API
        ResponseEntity<List<MessageResponse>> createdMessages = restTemplate().exchange(
                RequestEntity.get(URI.create("/messages")).build(),
                new ParameterizedTypeReference<>() {});

        // verify previously created messages are present
        assertEquals(HttpStatus.OK, createdMessages.getStatusCode());
        assertNotNull(createdMessages.getBody());
        assertEquals(messageRequests.size(), createdMessages.getBody().size());
    }

}
