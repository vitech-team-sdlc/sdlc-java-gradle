package com.vitech.moodfeed.user;

import com.vitech.moodfeed.RepoMediumTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryIT extends RepoMediumTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateAndGet() {
        User user = User.builder().id(1L).firstName("f-name").lastName("l-name").logoColor("#000000").build();
        userRepository.save(user);
        assertEquals(user, userRepository.findById(1L).get());
    }

}
