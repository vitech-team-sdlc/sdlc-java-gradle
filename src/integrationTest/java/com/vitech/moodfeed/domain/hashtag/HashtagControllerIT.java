package com.vitech.moodfeed.domain.hashtag;

import com.vitech.moodfeed.WebMediumTest;
import com.vitech.moodfeed.domain.message.dto.MessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashtagControllerIT extends WebMediumTest {

    @Test
    void testFindTopUsed() {
        // save few messages with hash tags by REST API
        Arrays.asList(
                MessageRequest.builder().creatorId(1L).message("message with #tag1 and some text").build(),
                MessageRequest.builder().creatorId(1L).message("another message with #tag2").build(),
                MessageRequest.builder().creatorId(1L).message("one more message with #tag2 and some text").build(),
                MessageRequest.builder().creatorId(1L).message("last message with both #tag1 and #tag2").build()
        ).forEach(msg -> restTemplate().postForEntity("/messages", msg, Void.class));

        // get top used hash tags by REST API
        ResponseEntity<List<String>> response = restTemplate().exchange(
                RequestEntity.get(URI.create("/hashtags")).build(),
                new ParameterizedTypeReference<List<String>>() {});

        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("tag2", "tag1"), response.getBody());
    }

}
