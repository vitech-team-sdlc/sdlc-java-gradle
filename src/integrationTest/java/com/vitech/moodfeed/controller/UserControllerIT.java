package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.MediumTest;
import com.vitech.moodfeed.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIT extends MediumTest {

    @Test
    void testGetLoggedUser() {
        // test
        ResponseEntity<User> response = restTemplate().getForEntity(baseUrl() + "/users/auth", User.class);
        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

}
