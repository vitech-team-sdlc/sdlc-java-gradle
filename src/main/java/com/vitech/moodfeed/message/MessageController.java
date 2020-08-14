package com.vitech.moodfeed.message;

import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
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

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    @GetMapping
    public List<MessageResponse> getMessages(@RequestParam(defaultValue = "10") int limit) {
        return Message.getNewest(limit, messageRepo, userRepo);
    }

    @PostMapping
    public void createMessage(@RequestBody MessageRequest request) {
        messageRepo.save(Message.fromRequest(request));
    }

}
