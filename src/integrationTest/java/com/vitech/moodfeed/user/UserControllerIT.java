package com.vitech.moodfeed.user;

import com.vitech.moodfeed.WebMediumTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIT extends WebMediumTest {

    @Test
    void testGetLoggedUser() {
        // test
        ResponseEntity<User> response = restTemplate().getForEntity("/users/auth", User.class);
        // verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

}
