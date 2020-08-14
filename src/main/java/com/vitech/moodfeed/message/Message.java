package com.vitech.moodfeed.message;

import com.vitech.moodfeed.message.dto.MessageRequest;
import com.vitech.moodfeed.message.dto.MessageResponse;
import com.vitech.moodfeed.user.UserRepository;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.vitech.moodfeed.utils.ModelMapperFactory.mapper;

@Value
@Builder
public class Message {

    @Id
    Long id;
    @Column("message")
    String body;
    Long creatorId;
    Date createdAt;

    public static Message fromRequest(MessageRequest request) {
        return mapper().map(request, Message.MessageBuilder.class).build();
    }

    public MessageResponse toResponse(UserRepository repo) {
        return repo.findById(creatorId)
                .map(
                        u -> mapper().map(this, MessageResponse.MessageResponseBuilder.class).creator(u).build()
                ).orElseThrow(() -> new RuntimeException("User by id=" + creatorId + " not found!"));
    }

    public static List<MessageResponse> getNewest(int limit, MessageRepository mRepo, UserRepository uRepo) {
        // query messages
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return mRepo.findAll(pageRequest).stream()
                .map(m -> m.toResponse(uRepo)).collect(Collectors.toList());
    }

    public void save(MessageRepository repo) {
        repo.save(this);
    }
}
