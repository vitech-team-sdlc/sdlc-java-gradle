package com.vitech.moodfeed.service.impl;

import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.dto.MessageResponse;
import com.vitech.moodfeed.repo.MessageRepository;
import com.vitech.moodfeed.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<MessageResponse> getMessages(int limit) {
        // TODO: join user information
        // TODO: add limit to query
        return null;
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }

}
