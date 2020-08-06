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
            new User(UUID.fromString("2d2b65d6-5ba9-4030-aeb4-4dbb1ebb19c1"), "Homer", "Simpson", "#faf7af"),
            new User(UUID.fromString("86a6ec1b-016c-45c6-9673-3d7edfd64b40"), "Marge", "Simpson", "#b167e3"),
            new User(UUID.fromString("badd30e2-0357-40d5-83e8-c04adb1b2e90"), "Bart", "Simpson", "#fad8af"),
            new User(UUID.fromString("cc08ace8-e2cf-486b-9da8-52a4ee3d446c"), "Lisa", "Simpson", "#f0c5eb"),
            new User(UUID.fromString("c37924fb-1fba-4bad-a70b-9691dad1111f"), "Ned", "Flanders", "#afe0fa"),
            new User(UUID.fromString("37679e91-aaae-48b1-b224-a0b31df3ae26"), "Barney", "Gumble", "#4002d8"),
            new User(UUID.fromString("ea38bb4d-18cd-49a9-8bfa-cabed3463a83"), "Moe", "Szyslak", "#1aa264"),
            new User(UUID.fromString("ac1af488-7e79-4566-a645-90d78e3e3b4f"), "Seymour", "Skinner", "#669bd9"),
            new User(UUID.fromString("4ab902f9-2db5-4cd2-b2e3-55c4afe4e530"), "Kent", "Brockman", "#a3b8aa")
    );

    @Override
    public User getLoggedUser() {
        return USERS.get(RANDOM.nextInt(USERS.size()));
    }

}
