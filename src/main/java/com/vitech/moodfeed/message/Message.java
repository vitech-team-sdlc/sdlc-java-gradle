package com.vitech.moodfeed.message;

import com.vitech.moodfeed.message.dto.Request;
import com.vitech.moodfeed.message.dto.Response;
import com.vitech.moodfeed.user.UserRepository;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
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

    public static Message fromRequest(Request request) {
        return mapper().map(request, Message.MessageBuilder.class).build();
    }

    public Response toResponse(UserRepository uRepo) {
        return uRepo.findById(creatorId)
                .map(u -> mapper().map(this, Response.ResponseBuilder.class).creator(u).build())
                .orElseThrow(() -> new RuntimeException("User by id = " + creatorId + " not found!"));
    }

    public static List<Response> getNewest(PageRequest pageReq, MessageRepository mRepo, UserRepository uRepo) {
        return mRepo.findAll(pageReq).stream().map(m -> m.toResponse(uRepo)).collect(Collectors.toList());
    }

    public void save(MessageRepository repo) {
        repo.save(this);
    }
}
