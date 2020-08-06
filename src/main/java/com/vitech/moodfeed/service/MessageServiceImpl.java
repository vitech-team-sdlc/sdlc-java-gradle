package com.vitech.moodfeed.service;

import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> getMessages(int limit) {
        return null;
    }

    @Override
    public void createMessage(Message message) {
    }

}
