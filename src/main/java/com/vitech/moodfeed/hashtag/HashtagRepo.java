package com.vitech.moodfeed.hashtag;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface HashtagRepo extends CrudRepository<Hashtag, String> {

    Set<Hashtag> findAllByMessageId(Long messageId);

}
