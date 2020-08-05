package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public User getUser() {
        return null;
    }

}
