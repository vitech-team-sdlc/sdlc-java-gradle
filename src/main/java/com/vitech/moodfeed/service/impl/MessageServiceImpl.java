package com.vitech.moodfeed.service.impl;

import com.google.common.collect.Lists;
import com.vitech.moodfeed.domain.Message;
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
    public List<Message> getMessages(int limit) {
        // TODO: join user information
        // TODO: add limit to query
        return Lists.newArrayList(messageRepository.findAll());
    }

    @Override
    public Message createMessage(Message message) {
        message.setId(null);
        message.setCreatedAt(null);
        return messageRepository.save(message);
    }

}
