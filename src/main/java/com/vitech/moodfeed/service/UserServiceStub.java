package com.vitech.moodfeed.service;

import com.vitech.moodfeed.domain.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Stub implementation of {@link UserService} which works with hardcoded list of users
 */
@Service
public class UserServiceStub implements UserService {

    private static final Random RANDOM = new Random();

    private static final List<User> USERS = Arrays.asList(
            new User(UUID.randomUUID(), "Homer", "Simpson", "#faf7af"),
            new User(UUID.randomUUID(), "Marge", "Simpson", "#b167e3"),
            new User(UUID.randomUUID(), "Bart", "Simpson", "#fad8af"),
            new User(UUID.randomUUID(), "Lisa", "Simpson", "#f0c5eb"),
            new User(UUID.randomUUID(), "Ned", "Flanders", "#afe0fa"),
            new User(UUID.randomUUID(), "Barney", "Gumble", "#4002d8"),
            new User(UUID.randomUUID(), "Moe", "Szyslak", "#1aa264"),
            new User(UUID.randomUUID(), "Seymour", "Skinner", "#669bd9"),
            new User(UUID.randomUUID(), "Kent", "Brockman", "#a3b8aa")
    );

    @Override
    public User getLoggedUser() {
        return USERS.get(RANDOM.nextInt(USERS.size()));
    }

}
