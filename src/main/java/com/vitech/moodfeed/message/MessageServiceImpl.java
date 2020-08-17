package com.vitech.moodfeed.message;

import com.google.common.collect.Lists;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;

    @Override
    public List<MessageResponse> getMessages(int limit) {
        // query messages
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Message> messages = Lists.newArrayList(messageRepo.findAll(pageRequest));
        // return messages enhanced with information about it's creators
        return messages.stream()
                .map(msg -> MessageResponse.from(userRepo.findById(msg.getCreatorId()).orElse(null), msg))
                .collect(Collectors.toList());
    }

    @Override
    public void createMessage(Message message) {
        messageRepo.save(message);
    }

}
