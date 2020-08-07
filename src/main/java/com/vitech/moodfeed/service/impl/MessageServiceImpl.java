package com.vitech.moodfeed.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vitech.moodfeed.domain.Message;
import com.vitech.moodfeed.domain.User;
import com.vitech.moodfeed.dto.MessageResponse;
import com.vitech.moodfeed.repo.MessageRepository;
import com.vitech.moodfeed.repo.UserRepository;
import com.vitech.moodfeed.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<MessageResponse> getMessages(int limit) {
        // query messages
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Message> messages = Lists.newArrayList(messageRepository.findAll(pageRequest));
        // query messages creators
        List<Long> creatorIds = messages.stream().map(Message::getCreatorId).collect(Collectors.toList());
        Map<Long, User> creators = Maps.uniqueIndex(userRepository.findAllById(creatorIds), User::getId);
        // return messages enhanced with information about it's creators
        return messages.stream()
                .map(msg -> MessageResponse.from(creators.get(msg.getCreatorId()), msg))
                .collect(Collectors.toList());
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }

}
