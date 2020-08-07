package com.vitech.moodfeed.controller;

import com.vitech.moodfeed.dto.MessageRequest;
import com.vitech.moodfeed.dto.MessageResponse;
import com.vitech.moodfeed.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<MessageResponse> getMessages(@RequestParam(defaultValue = "10") int limit) {
        return messageService.getMessages(limit);
    }

    @PostMapping
    public void createMessage(@RequestBody MessageRequest messageRequest) {
        messageService.createMessage(messageRequest.toMessage());
    }

}
