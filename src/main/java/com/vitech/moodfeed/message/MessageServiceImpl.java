package com.vitech.moodfeed.message;

import com.vitech.moodfeed.hashtag.HashtagService;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserService userService;
    private final HashtagService hashtagService;
    private final MessageRepository messageRepo;

    @Override
    public List<MessageResponse> getMessages(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return messageRepo.findAll(pageRequest).stream()
                .map(msg -> MessageResponse.from(
                        msg,
                        userService.findById(msg.getCreatorId()),
                        hashtagService.findAllByMessageId(msg.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public void createMessage(Message message) {
        Message savedMessage = messageRepo.save(message);
        hashtagService.saveTags(savedMessage);
    }

}
