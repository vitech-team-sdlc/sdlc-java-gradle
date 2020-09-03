package com.vitech.moodfeed.domain.message;

import com.vitech.moodfeed.domain.message.dto.MessageRequest;
import com.vitech.moodfeed.domain.message.dto.MessageResponse;
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
        messageService.createMessage(Message.fromRequest(messageRequest));
    }

}
