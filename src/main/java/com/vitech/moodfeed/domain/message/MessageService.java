package com.vitech.moodfeed.domain.message;

import com.vitech.moodfeed.domain.hashtag.HashtagRepository;
import com.vitech.moodfeed.domain.message.dto.MessageResponse;
import com.vitech.moodfeed.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;
    private final HashtagRepository hashtagRepo;

    public List<MessageResponse> getMessages(int limit) {
        Sort sort = Sort.by(Sort.Direction.fromString(Message.SORT_ORDER), Message.SORT_FIELD);
        PageRequest pageRequest = PageRequest.of(0, limit, sort);
        return messageRepo.findAll(pageRequest).stream()
                .map(msg -> msg.toResponse(
                        userRepo.findById(msg.getCreatorId()).orElse(null),
                        hashtagRepo.findAllByMessageId(msg.getId())))
                .collect(Collectors.toList());
    }

    public void createMessage(Message message) {
        Message savedMessage = messageRepo.save(message);
        savedMessage.extractHashTags().forEach(hashtagRepo::save);
    }

}
