package com.vitech.moodfeed.message;

import com.vitech.moodfeed.RepoRegistry;
import com.vitech.moodfeed.hashtag.Hashtag;
import com.vitech.moodfeed.message.dto.Request;
import com.vitech.moodfeed.message.dto.Response;
import com.vitech.moodfeed.user.User;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;
import java.util.List;

import static com.vitech.moodfeed.utils.ModelMapperFactory.mapper;
import static java.util.stream.Collectors.toList;

@Slf4j
@Value
@Builder
public class Message {

    @Id
    Long id;
    @Column("message")
    String body;
    Long creatorId;
    Date createdAt;

    public static void save(Request request, RepoRegistry registry) {
        Validate.notNull(request, "message must be set!");
        Validate.notEmpty(request.getBody(), "message.body must be set!");
        Validate.notNull(request.getCreatorId(), "message.creatorId must be set!");
        //save message and get saved one with propagated identifier
        Message saved = registry.getMessageRepo().save(mapper().map(request, Message.MessageBuilder.class).build());
        log.info("{} has been saved", saved);
        //save tags referencing to saved message
        Hashtag.saveTags(saved.getId(), request, registry);
    }

    public static List<Response> getNewest(PageRequest pageReq, RepoRegistry registry) {
        return registry.getMessageRepo().findAll(pageReq).stream().map(m -> m.toResponse(registry)).collect(toList());
    }

    public Response toResponse(RepoRegistry registry) {
        return mapper().map(this, Response.ResponseBuilder.class)
                .creator(User.findById(getCreatorId(), registry))
                .hashtags(Hashtag.findAllByMessageId(getId(), registry))
                .build();
    }
}
