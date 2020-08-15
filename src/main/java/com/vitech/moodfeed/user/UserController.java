package com.vitech.moodfeed.user;

import com.vitech.moodfeed.RepoRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RepoRegistry registry;

    @GetMapping("/auth")
    public User getLoggedUser() {
        return User.getRandom(registry);
    }

}
