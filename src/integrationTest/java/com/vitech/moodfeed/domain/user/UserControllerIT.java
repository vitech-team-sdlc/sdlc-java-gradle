package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.WebMediumTest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIT extends WebMediumTest {

    @Test
    void testGetUsers() {
        // test
        ResponseEntity<List<User>> response = restTemplate(KEYCLOAK_ROLE_ADMIN)
                .exchange(
                        RequestEntity.get(URI.create("/users")).build(),
                        new ParameterizedTypeReference<>() {});
        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetCurrent() {
        // test
        ResponseEntity<User> response = restTemplate()
                .exchange(
                        RequestEntity.get(URI.create("/users/current")).build(),
                        new ParameterizedTypeReference<>() {});
        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
