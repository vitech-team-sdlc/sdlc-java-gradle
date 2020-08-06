package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.domain.User;
import com.vitech.moodfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth")
    public User geLoggedUser() {
        return userService.getLoggedUser();
    }

}
