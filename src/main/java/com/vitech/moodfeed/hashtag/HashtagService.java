package com.vitech.moodfeed.hashtag;

import com.vitech.moodfeed.message.Message;

import java.util.List;
import java.util.Set;

public interface HashtagService {

    /**
     * Extract hash tags from message and save
     *
     * @param message message to extract hash tags from
     */
    void saveTags(Message message);

    /**
     * Find all hash tags related to specified message
     *
     * @param messageId id of message to find hash tags for
     * @return all hash tags related to specified message
     */
    Set<String> findAllByMessageId(Long messageId);

    /**
     * Find top used hash tags
     *
     * @param limit maximum number of hash tags
     * @return limited list of top used hash tags
     */
    List<String> findTopUsed(int limit);

}
