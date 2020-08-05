package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.domain.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private static final List<Message> MESSAGES_DB = new ArrayList<>();

    @GetMapping
    public List<Message> getMessages() {
        return MESSAGES_DB;
    }

    @PostMapping
    public void addMessage(Message message) {
        MESSAGES_DB.add(message);
    }

}
