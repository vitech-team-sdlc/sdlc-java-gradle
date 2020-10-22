package com.vitech.moodfeed.domain.user;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public List<User> getUsers() {
        return ImmutableList.copyOf(userRepo.findAll());
    }

}
