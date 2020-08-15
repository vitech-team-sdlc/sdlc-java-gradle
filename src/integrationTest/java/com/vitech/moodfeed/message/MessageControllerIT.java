package com.vitech.moodfeed.message;

import com.vitech.moodfeed.WebMediumTest;
import com.vitech.moodfeed.message.dto.Request;
import com.vitech.moodfeed.message.dto.Response;
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
    private MessageRepo messageRepo;

    @BeforeEach
    public void beforeEach() {
        messageRepo.deleteAll();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 7})
    void testCreateAndGetMessages(int limit) {

        // generate messages
        List<Request> requests = LongStream.range(1, 5)
                .mapToObj(num -> Request.builder().body("Test message #cool #stuff!" + num).creatorId(num).build())
                .collect(Collectors.toList());

        // create generated messages by REST API and verify response is OK
        requests.forEach(messageRequest -> assertEquals(
                HttpStatus.OK,
                restTemplate().postForEntity(messagesUri(), messageRequest, Void.class).getStatusCode()));

        // get all messages by REST API
        ResponseEntity<List<Response>> createdMessages = restTemplate().exchange(
                RequestEntity.get(messagesUriWithLimit(limit)).build(),
                new ParameterizedTypeReference<List<Response>>() {});

        // verify previously created messages are present and limit is working as expected
        assertEquals(HttpStatus.OK, createdMessages.getStatusCode());
        assertNotNull(createdMessages.getBody());
        assertEquals(Math.min(limit, requests.size()), createdMessages.getBody().size());
    }

    private URI messagesUri() {
        return messagesUriWithLimit(null);
    }

    private URI messagesUriWithLimit(Integer limit) {
        return URI.create(baseUrl() + "/messages" + (Objects.nonNull(limit) ? "?limit=" + limit : ""));
    }

}
