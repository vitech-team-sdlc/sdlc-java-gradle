package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.WebMediumTest;
import com.vitech.moodfeed.message.Message;
import com.vitech.moodfeed.message.dto.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HashtagControllerIT extends WebMediumTest {

    @BeforeEach
    public void before() {
        //tag3 tag2 tag1 - order
        Message.save(Request.builder().body("#tag1 #tag2 #tag3").creatorId(1L).build(), registry);
        Message.save(Request.builder().body("#tag2 #tag3").creatorId(1L).build(), registry);
        Message.save(Request.builder().body("#tag3").creatorId(1L).build(), registry);
    }

    @ParameterizedTest
    @MethodSource(value = "findTopUsedSource")
    void findTopUsed(Integer limit, List<String> expected) {

        ResponseEntity<List<String>> response = restTemplate().exchange(
                RequestEntity.get(uri(limit)).build(),
                new ParameterizedTypeReference<List<String>>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), expected);
        assertNotNull(response.getBody());
    }

    private static Stream<Arguments> findTopUsedSource() {
        return Stream.of(
                Arguments.of(2, asList("tag3", "tag2"))
                , Arguments.of(10, asList("tag3", "tag2", "tag1"))
                //null == default (10)
                , Arguments.of(null, asList("tag3", "tag2", "tag1"))
        );
    }


    private URI uri(Integer limit) {
        return URI.create(baseUrl() + "/hashtags" + (Objects.nonNull(limit) ? "?limit=" + limit : ""));
    }
}
