package com.vitech.moodfeed.user;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Implementation of {@link UserService} interface
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Random RANDOM = new Random();
    private final UserRepository userRepository;

    @Override
    public User getLoggedUser() {
        // get all users
        List<User> allUsers = Lists.newArrayList(userRepository.findAll());
        // return random one
        return allUsers.get(RANDOM.nextInt(allUsers.size()));
    }

}
