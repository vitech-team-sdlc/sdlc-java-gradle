package com.vitech.moodfeed.service;

import com.vitech.moodfeed.domain.Message;

import java.util.List;

public interface MessageService {

    /**
     * Get messages posted by users
     *
     * @param limit maximum number of messages
     * @return limited list of messages posted by users
     */
    List<Message> getMessages(int limit);

    /**
     * Create new message
     *
     * @param message message to create
     */
    void createMessage(Message message);

}
