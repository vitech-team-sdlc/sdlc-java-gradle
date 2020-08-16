package com.vitech.moodfeed.hashtag;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface HashtagRepo extends CrudRepository<Hashtag, String> {

    Set<Hashtag> findAllByMessageId(Long messageId);

    @Query("select tag from hashtag group by tag order by count(1) desc, tag asc limit :limit")
    List<String> findTopUsed(Integer limit);
}
