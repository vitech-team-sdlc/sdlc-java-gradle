package com.vitech.moodfeed.domain.user;

import com.vitech.moodfeed.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User getLoggedUser() {
        return Utils.getRandom(userRepo.findAll());
    }

}
