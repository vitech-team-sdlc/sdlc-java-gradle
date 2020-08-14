package com.vitech.moodfeed.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    @GetMapping("/auth")
    public User getLoggedUser() {
        return User.getRandom(repo);
    }

}
