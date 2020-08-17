package com.vitech.moodfeed.user;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Random RANDOM = new Random();
    private final UserRepository userRepo;

    @Override
    public User getLoggedUser() {
        // get all users
        List<User> allUsers = Lists.newArrayList(userRepo.findAll());
        // return random one
        return allUsers.get(RANDOM.nextInt(allUsers.size()));
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User by id = " + id + "not found!"));
    }
}
