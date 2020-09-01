package com.vitech.moodfeed.domain.message;

import com.vitech.moodfeed.RepoMediumTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MessageRepositoryIT extends RepoMediumTest<Message> {

    @Autowired
    public MessageRepositoryIT(MessageRepository messageRepo) {
        super(messageRepo, new CrudTestDataProvider<>(
                Message::getId,
                () -> Message.builder().message("msg").creatorId(1L).createdAt(currentDateTime()).build(),
                (id) -> Message.builder().id(id).message("u-msg").creatorId(1L).createdAt(currentDateTime()).build()
        ));
    }

    private static LocalDateTime currentDateTime() {
        return LocalDateTime.now().withNano(0);
    }

}
